package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.github.lopoha.coboltruffle.heap.HeapBuilder;
import com.github.lopoha.coboltruffle.heap.HeapBuilderVariable;
import com.github.lopoha.coboltruffle.heap.HeapPointer;
import com.github.lopoha.coboltruffle.heap.HeapVariableType;
import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolFunctionLiteralNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolStringLiteralNode;
import com.github.lopoha.coboltruffle.parser.antlr.CobolBaseListener;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser.IfConditionContext;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser.VariableDataTypeContext;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser.VariableDataTypeNumberContext;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser.VariableDataTypeStringContext;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser.VariableDefinitionContext;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser.VariableRedefinesContext;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser.VariableSizeContext;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser.VariableValueNumberContext;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser.VariableValueStringContext;
import com.oracle.truffle.api.RootCallTarget;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


// TODO: Move create separate exceptions instead of reusing runtimeexception!
// todo: cleanup, merge methods, ...
// todo: is there a difference between e.g. no value and value space? or 0?
// todo: should all numbers be handled as floats? and integers are floats with no decimal place?
// todo: multiple classes
// todo: can a copy member don't start at level 01? and the following not declaration start at 01?
//       if not, the preprocessor should put info in, when a copy starts/ends,
//       so this can be checked...
// todo: how should the linkage heap be handled? this could also be implemented as parameters
//       that gets passed in whe calling. (like a constructor)

public class CobolBaseListenerImpl extends CobolBaseListener {
  private final HeapBuilder workingStorageHeap = new HeapBuilder();
  private final HeapBuilder linkageHeap = new HeapBuilder();
  private final CobolLanguage cobolLanguage;
  private final CobolNodeFactory cobolNodeFactory;
  private String programName;

  /**
   * Creates the Listener, that walks the Tokens and creates the coboltruffle classes.
   * @param cobolLanguage a reference to the cobollanguage
   */
  public CobolBaseListenerImpl(CobolLanguage cobolLanguage) {
    assert cobolLanguage != null;
    this.cobolLanguage = cobolLanguage;
    this.cobolNodeFactory = new CobolNodeFactory(cobolLanguage);
  }

  @Override
  public void enterFile(CobolParser.FileContext ctx) {
    System.out.println("And so the hunt begins again...");
  }

  @Override
  public void exitFile(CobolParser.FileContext ctx) {
    System.out.println("Are you cold...? Oh, good hunter,");
  }

  @Override
  public void enterProgramID(CobolParser.ProgramIDContext ctx) {
    this.programName = ctx.ID().getText();
  }

  private int variableGetStringLength(VariableDataTypeStringContext ctx) {
    if (ctx.PICXS() != null) {
      return ctx.PICXS().getText().length();
    } else {
      VariableSizeContext sizeContext = ctx.variableSize();
      return (sizeContext == null || sizeContext.NUMBER() == null)
             ? 1
             : Integer.parseInt(sizeContext.NUMBER().getText());
    }
  }


  // either returns null if no value was specified or a string with the length of the given size
  // and the value right bound + filled with space if it is shorter.
  private String variableGetStringValue(VariableValueStringContext ctx,
                                        String variableName, int size) {
    // todo: cleanup
    if (ctx == null) {
      return null;
    } else if (ctx.SPACE() != null) {
      return " ".repeat(size);
    } else {
      String string = ctx.STRING().getText();
      String removedQuotes = string.substring(1, string.length() - 1);
      if (removedQuotes.length() <= size) {
        return " ".repeat(size - removedQuotes.length()) + removedQuotes;
      } else {
        throw new RuntimeException(String.format("The variable \"%s\" with the given length [%d] "
                + "was too small for \"\" (length: [%d]",
            variableName,
            size,
            removedQuotes.length()));
      }
    }
  }

  private int variableGetNumberSize(VariableDataTypeNumberContext ctx) {
    if (ctx.PIC9S() != null) {
      return ctx.PIC9S().getText().length();
    } else {
      VariableSizeContext sizeContext = ctx.variableSize();
      return (sizeContext == null || sizeContext.NUMBER() == null)
              ? 1
              : Integer.parseInt(sizeContext.NUMBER().getText());
    }
  }

  // either returns null if no value was specified or a string with the length of the given size
  // and the value right bound + filled with space if it is shorter.
  private String variableGetNumberValue(VariableValueNumberContext ctx,
                                        String variableName,
                                        int size) {
    // todo: cleanup
    if (ctx == null) {
      return null;
    } else {
      String string = ctx.NUMBER().getText();
      if (string.length() <= size) {
        return " ".repeat(size - string.length()) + string;
      } else {
        throw new RuntimeException(String.format("The variable \"%s\" with the given length [%d] "
            + "was too small for \"\" (length: [%d]", variableName, size, string.length()));
      }
    }
  }


  // todo: cleanup!!! + names + split
  private void addVariableDefinition(VariableDefinitionContext variableDefinitionContext,
                                     HeapBuilder heapBuilder) {
    final int level = Integer.parseInt(variableDefinitionContext.NUMBER().getText());
    final VariableRedefinesContext redefinesContext = variableDefinitionContext.variableRedefines();
    final String redefines = redefinesContext == null ? null : redefinesContext.ID().getText();

    if (variableDefinitionContext.FILLER() != null) {
      final VariableDataTypeContext dataType = variableDefinitionContext.variableDataType();
      if (dataType == null) {
        final HeapBuilderVariable variable = new HeapBuilderVariable(level,
                                                          null,
                                                                     HeapVariableType.Filler,
                                                                     redefines);
        heapBuilder.add(variable);
      } else {
        VariableDataTypeStringContext dataTypeString = dataType.variableDataTypeString();
        VariableDataTypeNumberContext dataTypeNumber = dataType.variableDataTypeNumber();
        if (dataTypeString != null) {
          final int size = variableGetStringLength(dataTypeString);
          final String value
              = variableGetStringValue(dataTypeString.variableValueString(), null, size);
          final HeapBuilderVariable variable
              = new HeapBuilderVariable(level, null, HeapVariableType.Filler, size, value);
          heapBuilder.add(variable);
        } else if (dataTypeNumber != null) {
          // todo: allow comma values e.g. 999.99
          final int size = variableGetNumberSize(dataTypeNumber);
          final String value
              = variableGetNumberValue(dataTypeNumber.variableValueNumber(), null, size);
          final HeapBuilderVariable variable
              = new HeapBuilderVariable(level, null, HeapVariableType.Filler, size, value);
          heapBuilder.add(variable);
        } else {
          throw new NotImplementedException();
        }
      }
    } else {
      final String variableName = variableDefinitionContext.ID().getText();
      final VariableDataTypeContext dataType = variableDefinitionContext.variableDataType();
      if (dataType == null) {
        // todo: handle redefines
        final HeapBuilderVariable variable
            = new HeapBuilderVariable(level, variableName, HeapVariableType.None, redefines);
        heapBuilder.add(variable);
      } else {
        // todo: allow array (table)
        VariableDataTypeStringContext dataTypeString = dataType.variableDataTypeString();
        VariableDataTypeNumberContext dataTypeNumber = dataType.variableDataTypeNumber();
        if (dataTypeString != null) {
          final int size = variableGetStringLength(dataTypeString);
          final String value
              = variableGetStringValue(dataTypeString.variableValueString(), variableName, size);
          final HeapBuilderVariable variable
              = new HeapBuilderVariable(level, variableName, HeapVariableType.String, size, value);
          heapBuilder.add(variable);
        } else if (dataTypeNumber != null) {
          // todo: allow comma values e.g. 999.99
          final int size = variableGetNumberSize(dataTypeNumber);
          final String value
              = variableGetNumberValue(dataTypeNumber.variableValueNumber(), variableName, size);
          final HeapBuilderVariable variable
              = new HeapBuilderVariable(level, variableName, HeapVariableType.Number, size, value);
          heapBuilder.add(variable);
        } else {
          throw new NotImplementedException();
        }
      }
    }
  }

  @Override
  public void enterWorkingStorageSection(CobolParser.WorkingStorageSectionContext ctx) {
    List<VariableDefinitionContext> variableDefinitions = ctx.variableDefinition();
    for (VariableDefinitionContext variableDefinitionContext : variableDefinitions) {
      addVariableDefinition(variableDefinitionContext, this.workingStorageHeap);
    }
  }

  @Override
  public void enterLinkageSection(CobolParser.LinkageSectionContext ctx) {
    List<VariableDefinitionContext> variableDefinitions = ctx.variableDefinition();
    for (VariableDefinitionContext variableDefinitionContext : variableDefinitions) {
      addVariableDefinition(variableDefinitionContext, this.linkageHeap);
    }
  }

  @Override
  public void exitDataDivision(CobolParser.DataDivisionContext ctx) {
    this.cobolLanguage.addToHeap(this.workingStorageHeap);
  }

  @Override
  public void enterIfStatement(CobolParser.IfStatementContext ctx) {
    System.out.println("IF");
    IfConditionContext conditionContext = ctx.ifCondition();
    if (conditionContext.ifNumeric() != null) {
      System.out.println("Numeric");
    } else if (conditionContext.ifCompare() != null) {
      System.out.println("Comparison");
    } else if (conditionContext.ifSingleValue() != null) {
      System.out.println("Single Value");
    } else {
      throw new RuntimeException("Unhandled if condition");
    }
  }

  @Override
  public void enterElseBranch(CobolParser.ElseBranchContext ctx) {
    System.out.println("ELSE");
  }

  @Override
  public void enterEndIf(CobolParser.EndIfContext ctx) {
    System.out.println("END IF");
  }

  @Override
  public void enterMoveStatement(CobolParser.MoveStatementContext ctx) {
    CobolParser.MoveToContext moveToContext = ctx.moveTo();
    // todo allow multiple targets
    String targetString = moveToContext.ID(0).toString();
    HeapPointer targetPointer = this.cobolLanguage.heapGetVariable(targetString);

    CobolParser.MoveFromContext moveFromContext = ctx.moveFrom();
    if (moveFromContext.ID() != null) {
      HeapPointer fromPointer = this.cobolLanguage.heapGetVariable(moveFromContext.ID().toString());
      this.cobolNodeFactory.addMove(fromPointer, targetPointer);
    } else if (moveFromContext.STRING() != null) {
      String inputString = removeStringQuotes(moveFromContext.STRING().getText());
      this.cobolNodeFactory.addMove(inputString, targetPointer);
    } else {
      // todo: how should number be implemented?
      //       and should space be implemented specially or should it just have a value
      //       and behave like a string with blanks was given?
      throw new NotImplementedException();
    }
  }

  @Override
  public void enterDisplayStatement(CobolParser.DisplayStatementContext ctx) {
    System.out.println("DISPLAY");

    List<CobolExpressionNode> displayArgs = new ArrayList<>();
    for (CobolParser.DisplayParameterContext displayParameter : ctx.displayParameter()) {
      if (displayParameter.ID() != null) {
        HeapPointer fromPointer
            = this.cobolLanguage.heapGetVariable(displayParameter.ID().toString());
        displayArgs.add(fromPointer);
      } else if (displayParameter.STRING() != null) {
        String inputString = removeStringQuotes(displayParameter.STRING().getText());
        CobolStringLiteralNode stringConstant = new CobolStringLiteralNode(inputString);
        displayArgs.add(stringConstant);
      } else {
        throw new NotImplementedException();
      }
    }

    CobolFunctionLiteralNode displayNode
        = new CobolFunctionLiteralNode(this.programName, "display");
    this.cobolNodeFactory.addCall(displayNode, displayArgs);
  }

  @Override
  public void enterInitializeStatement(CobolParser.InitializeStatementContext ctx) {
    String variable = ctx.ID().getText();
    HeapPointer heapPointer = this.cobolLanguage.heapGetVariable(variable);
    this.cobolNodeFactory.addInitialize(heapPointer);
  }

  private String removeStringQuotes(String input) {
    final int start = input.startsWith("\"") ? 1 : 0;
    assert start <= 0 || input.endsWith("\"");
    return input.substring(start, input.length() - start);
  }

  public Map<String, RootCallTarget> getAllSections() {
    return this.cobolNodeFactory.getAllSections();
  }

  @Override
  public void enterFunctionCallStatement(CobolParser.FunctionCallStatementContext ctx) {
    CobolFunctionLiteralNode displayNode
        = new CobolFunctionLiteralNode(this.programName, ctx.ID().getText());
    // todo: should arguments be allowed here?
    this.cobolNodeFactory.addCall(displayNode, new ArrayList<>());
  }

  @Override
  public void enterFunctionSection(CobolParser.FunctionSectionContext ctx) {
    this.cobolNodeFactory.startSection(this.programName, ctx.functionSectionStart().ID().getText());
  }

  @Override
  public void exitFunctionSection(CobolParser.FunctionSectionContext ctx) {
    this.cobolNodeFactory.finishSection();
  }

  @Override
  public void exitProcedureDivision(CobolParser.ProcedureDivisionContext ctx) {
    this.cobolNodeFactory.createConstructor(this.programName);
  }
}