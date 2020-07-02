package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.github.lopoha.coboltruffle.heap.HeapBuilder;
import com.github.lopoha.coboltruffle.heap.HeapBuilderVariable;
import com.github.lopoha.coboltruffle.heap.HeapPointer;
import com.github.lopoha.coboltruffle.heap.HeapVariableType;
import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolFunctionLiteralNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolStringLiteralNode;
import com.github.lopoha.coboltruffle.nodes.expression.comparison.CobolBiggerOrEqualNodeGen;
import com.github.lopoha.coboltruffle.nodes.expression.comparison.CobolBiggerThanNodeGen;
import com.github.lopoha.coboltruffle.nodes.expression.comparison.CobolEqualNodeGen;
import com.github.lopoha.coboltruffle.nodes.expression.comparison.CobolLessOrEqualNodeGen;
import com.github.lopoha.coboltruffle.nodes.expression.comparison.CobolLessThanNodeGen;
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

  // todo: cleanup + combine with addNamedVariable
  private void addFillerVariable(HeapBuilder heapBuilder,
                                 String redefines,
                                 int level,
                                 VariableDataTypeContext dataType) {
    if (dataType == null) {
      final HeapBuilderVariable variable
          = new HeapBuilderVariable(level, null, HeapVariableType.Filler, redefines);
      heapBuilder.add(variable);

    } else if (dataType.variableDataTypeString() != null) {
      VariableDataTypeStringContext dataTypeString = dataType.variableDataTypeString();
      final int size = variableGetStringLength(dataTypeString);
      final String value
          = variableGetStringValue(dataTypeString.variableValueString(), null, size);
      final HeapBuilderVariable variable
          = new HeapBuilderVariable(level, null, HeapVariableType.Filler, size, value);
      heapBuilder.add(variable);

    } else if (dataType.variableDataTypeNumber() != null) {
      VariableDataTypeNumberContext dataTypeNumber = dataType.variableDataTypeNumber();
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

  // todo: cleanup + combine with addFillerVariable
  private void addNamedVariable(HeapBuilder heapBuilder,
                                String variableName,
                                int level,
                                VariableDataTypeContext dataType) {
    // todo: allow array (table)
    if (level == 88) {
      // level 88 => const


    } else if (dataType.variableDataTypeString() != null) {
      VariableDataTypeStringContext dataTypeString = dataType.variableDataTypeString();
      final int size = variableGetStringLength(dataTypeString);
      final String value
          = variableGetStringValue(dataTypeString.variableValueString(), variableName, size);
      final HeapBuilderVariable variable
          = new HeapBuilderVariable(level, variableName, HeapVariableType.String, size, value);
      heapBuilder.add(variable);

    } else if (dataType.variableDataTypeNumber() != null) {
      VariableDataTypeNumberContext dataTypeNumber = dataType.variableDataTypeNumber();
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



  // todo: cleanup!!! + names + split
  private void addVariableDefinition(CobolParser.VariableVariableContext variableContext,
                                     HeapBuilder heapBuilder) {
    final int level = Integer.parseInt(variableContext.NUMBER().getText());
    final VariableRedefinesContext redefinesContext = variableContext.variableRedefines();
    final String redefines = redefinesContext == null ? null : redefinesContext.ID().getText();

    if (variableContext.FILLER() != null) {
      final VariableDataTypeContext dataType = variableContext.variableDataType();
      addFillerVariable(heapBuilder, redefines, level, dataType);
    } else {
      final String variableName = variableContext.ID().getText();
      final VariableDataTypeContext dataType = variableContext.variableDataType();
      if (dataType == null) {
        // todo: handle redefines
        final HeapBuilderVariable variable
            = new HeapBuilderVariable(level, variableName, HeapVariableType.None, redefines);
        heapBuilder.add(variable);
      } else {
        addNamedVariable(heapBuilder, variableName, level, dataType);
      }
    }
  }

  @Override
  public void enterWorkingStorageSection(CobolParser.WorkingStorageSectionContext ctx) {
    List<VariableDefinitionContext> variableDefinitions = ctx.variableDefinition();
    for (VariableDefinitionContext variableDefinitionContext : variableDefinitions) {
      if (variableDefinitionContext.variableVariable() != null) {
        addVariableDefinition(variableDefinitionContext.variableVariable(),
                              this.workingStorageHeap);
      } else if (variableDefinitionContext.variableConst() != null) {
        CobolParser.VariableConstContext constContext = variableDefinitionContext.variableConst();
        HeapBuilderVariable parent = this.workingStorageHeap.getLastVariable();
        // todo: allow other values
        // todo: match size with parent
        String value = constContext.variableValueString().STRING().getText();
        final HeapBuilderVariable variable = new HeapBuilderVariable(88,
            constContext.ID().getText(),
            HeapVariableType.Const,
            parent.getSize(),
            value);
        this.workingStorageHeap.add(variable);
      } else {
        throw new NotImplementedException();
      }
    }
  }

  @Override
  public void enterLinkageSection(CobolParser.LinkageSectionContext ctx) {
    List<VariableDefinitionContext> variableDefinitions = ctx.variableDefinition();
    throw new NotImplementedException();
    // how should the linkage section work?
    /*
    for (VariableDefinitionContext variableDefinitionContext : variableDefinitions) {
      addVariableDefinition(variableDefinitionContext, this.linkageHeap);
    }
     */
  }

  @Override
  public void exitDataDivision(CobolParser.DataDivisionContext ctx) {
    this.cobolLanguage.addToHeap(this.workingStorageHeap);
  }

  @Override
  public void enterIfStatement(CobolParser.IfStatementContext ctx) {
    IfConditionContext conditionContext = ctx.ifCondition();
    if (conditionContext.ifNumeric() != null) {
      throw new NotImplementedException();
    } else if (conditionContext.ifCompare() != null) {
      CobolParser.IfCompareContext ifCompareContext = conditionContext.ifCompare();
      CobolExpressionNode left = valueToExpression(ifCompareContext.value(0));
      CobolExpressionNode right = valueToExpression(ifCompareContext.value(1));
      CobolExpressionNode condition = ifComparisonToExpression(ifCompareContext, left, right);
      this.cobolNodeFactory.startIf(condition);
    } else if (conditionContext.ifSingleValue() != null) {
      throw new NotImplementedException();
    } else {
      throw new NotImplementedException();
    }
  }


  @Override
  public void enterElseBranch(CobolParser.ElseBranchContext ctx) {
    this.cobolNodeFactory.elseIf();
  }

  @Override
  public void enterEndIf(CobolParser.EndIfContext ctx) {
    this.cobolNodeFactory.endIf();
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

  private CobolExpressionNode valueToExpression(CobolParser.ValueContext ctx) {
    if (ctx.ID() != null) {
      return this.cobolLanguage.heapGetVariable(ctx.ID().toString());
    } else if (ctx.SPACE() != null) {
      throw new NotImplementedException();
    } else if (ctx.STRING() != null) {
      throw new NotImplementedException();
    } else if (ctx.NUMBER() != null) {
      throw new NotImplementedException();
    } else {
      throw new NotImplementedException();
    }
  }

  private CobolExpressionNode ifComparisonToExpression(CobolParser.IfCompareContext ctx,
                                                       CobolExpressionNode left,
                                                       CobolExpressionNode right) {
    if (ctx.LESS() != null) {
      if (ctx.EQUAL() != null) {
        return CobolLessOrEqualNodeGen.create(left, right);
      } else {
        return CobolLessThanNodeGen.create(left, right);
      }
    } else if (ctx.BIGGER() != null) {
      if (ctx.EQUAL() != null) {
        return CobolBiggerOrEqualNodeGen.create(left, right);
      } else {
        return CobolBiggerThanNodeGen.create(left, right);
      }
    } else if (ctx.EQUAL() != null) {
      // must be last, because others could also include equal...
      System.out.println("equal");
      return CobolEqualNodeGen.create(left, right);
    } else {
      throw new NotImplementedException();
    }
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