package com.github.lopoha.coboltruffle.parser;

import static com.github.lopoha.coboltruffle.parser.CobolVariableDefinitionParser.addVariable;

import com.github.lopoha.coboltruffle.NotImplementedException;
import com.github.lopoha.coboltruffle.heap.CobolHeap;
import com.github.lopoha.coboltruffle.heap.HeapBuilder;
import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolGlobalFunctionLiteralNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolStringLiteralNode;
import com.github.lopoha.coboltruffle.nodes.expression.comparison.CobolBiggerOrEqualNodeGen;
import com.github.lopoha.coboltruffle.nodes.expression.comparison.CobolBiggerThanNodeGen;
import com.github.lopoha.coboltruffle.nodes.expression.comparison.CobolEqualNodeGen;
import com.github.lopoha.coboltruffle.nodes.expression.comparison.CobolLessOrEqualNodeGen;
import com.github.lopoha.coboltruffle.nodes.expression.comparison.CobolLessThanNodeGen;
import com.github.lopoha.coboltruffle.nodes.expression.heap.CobolHeapPointer;
import com.github.lopoha.coboltruffle.parser.antlr.CobolBaseListener;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser.IfConditionContext;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser.VariableDefinitionContext;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.source.Source;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.tree.ParseTree;



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

class CobolBaseListenerImpl extends CobolBaseListener {
  private final HeapBuilder workingStorageHeapBuilder = new HeapBuilder();
  private final List<String> includedCopyVariableDefinitions = new ArrayList<>();
  private final List<String> inputParameterCopyPaths = new ArrayList<>();
  private final CobolMainParser cobolMainParser;
  private final CobolNodeFactory cobolNodeFactory;
  private final CobolHeap workingStorageHeap = new CobolHeap();
  private final Source source;
  private String programName;

  /**
   * Creates the Listener, that walks the Tokens and creates the coboltruffle classes.
   * @param cobolMainParser a reference to the cobol main parser.
   */
  CobolBaseListenerImpl(CobolMainParser cobolMainParser, Source source) {
    assert cobolMainParser != null;
    assert source != null;
    this.cobolMainParser = cobolMainParser;
    this.cobolNodeFactory = new CobolNodeFactory(cobolMainParser.getCobolLanguage(), source);
    this.source = source;
  }

  @Override
  public void enterProgram(CobolParser.ProgramContext ctx) {
    System.out.println("And so the hunt begins again...");
  }

  @Override
  public void exitProgram(CobolParser.ProgramContext ctx) {
    this.cobolNodeFactory.createConstructor(this.programName,
                                            ctx.stop,
                                            this.workingStorageHeap,
                                            this.inputParameterCopyPaths);
    System.out.println("Are you cold...? Oh, good hunter,");
  }

  @Override
  public void enterProgramID(CobolParser.ProgramIDContext ctx) {
    this.programName = ctx.ID().getText();
  }


  @Override
  public void enterWorkingStorageSection(CobolParser.WorkingStorageSectionContext ctx) {
    for (ParseTree child : ctx.children) {
      if (child instanceof VariableDefinitionContext) {
        addVariable((VariableDefinitionContext) child, this.workingStorageHeapBuilder);
      } else if (child instanceof CobolParser.CopyContext) {
        CobolParser.CopyContext copyContext = (CobolParser.CopyContext) child;
        Source copySource
            = this.cobolMainParser.getCopySource(copyContext.ID().getText());
        HeapBuilder heapBuilder = this.cobolMainParser.processStorageCopy(copySource);
        this.workingStorageHeapBuilder.add(heapBuilder);
      }
    }
  }

  @Override
  public void enterLinkageSection(CobolParser.LinkageSectionContext ctx) {
    for (CobolParser.CopyContext copy : ctx.copy()) {
      Source copySource = this.cobolMainParser.getCopySource(copy.ID().getText());
      this.inputParameterCopyPaths.add(CobolMainParser.getFilenameWithoutExtension(copySource));
    }
    // how should the linkage section work?
    /*
    for (VariableDefinitionContext variableDefinitionContext : variableDefinitions) {
      addVariableDefinition(variableDefinitionContext, this.linkageHeap);
    }
     */
  }

  @Override
  public void exitDataDivision(CobolParser.DataDivisionContext ctx) {
    CobolProgramInfo cobolProgramInfo
        = new CobolProgramInfo(this.source, this.inputParameterCopyPaths);
    this.cobolMainParser.addProgram(cobolProgramInfo);

    this.workingStorageHeap.addToHeap(this.workingStorageHeapBuilder);
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
      CobolParser.TrueBranchContext trueBranch = ctx.trueBranch();
      this.cobolNodeFactory.startIf(ctx.start, trueBranch.start, condition);
    } else if (conditionContext.ifSingleValue() != null) {
      throw new NotImplementedException();
    } else {
      throw new NotImplementedException();
    }
  }


  @Override
  public void enterElseBranch(CobolParser.ElseBranchContext ctx) {
    this.cobolNodeFactory.elseIf(ctx.start);
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
    CobolHeapPointer targetPointer = this.workingStorageHeap.getHeapPointer(targetString);

    CobolParser.MoveFromContext moveFromContext = ctx.moveFrom();
    if (moveFromContext.ID() != null) {
      CobolHeapPointer fromPointer
          = this.workingStorageHeap.getHeapPointer(moveFromContext.ID().toString());
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
        CobolHeapPointer fromPointer
            = this.workingStorageHeap.getHeapPointer(displayParameter.ID().toString());
        displayArgs.add(fromPointer);
      } else if (displayParameter.STRING() != null) {
        String inputString = removeStringQuotes(displayParameter.STRING().getText());
        CobolStringLiteralNode stringConstant = new CobolStringLiteralNode(inputString);
        displayArgs.add(stringConstant);
      } else {
        throw new NotImplementedException();
      }
    }

    CobolGlobalFunctionLiteralNode displayNode
        = new CobolGlobalFunctionLiteralNode("display");
    this.cobolNodeFactory.addCall(ctx.start, displayNode, displayArgs);
  }

  @Override
  public void enterInitializeStatement(CobolParser.InitializeStatementContext ctx) {
    String variable = ctx.ID().getText();
    CobolHeapPointer heapPointer = this.workingStorageHeap.getHeapPointer(variable);
    this.cobolNodeFactory.addInitialize(heapPointer);
  }

  private String removeStringQuotes(String input) {
    final int start = input.startsWith("\"") ? 1 : 0;
    assert start <= 0 || input.endsWith("\"");
    return input.substring(start, input.length() - start);
  }

  public RootCallTarget getConstructor() {
    return this.cobolNodeFactory.getConstructor();
  }

  private CobolExpressionNode valueToExpression(CobolParser.ValueContext ctx) {
    if (ctx.ID() != null) {
      return this.workingStorageHeap.getHeapPointer(ctx.ID().toString());
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
    // TODO: IMPLEMENT LESSEQUAL, LESSTHAN as separate items
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
  public void enterExternalCallStatement(CobolParser.ExternalCallStatementContext ctx) {
    System.out.println("CALL " + ctx.externalCallProgramName().getText());
    String callProgramName = ctx.externalCallProgramName().getText();
    CobolProgramInfo calledProgram
        = this.cobolMainParser.getProgram(callProgramName);
    List<String> params = ctx.externalCallInputParameter()
                             .ID()
                             .stream()
                             .map(ParseTree::getText)
                             .collect(Collectors.toList());

    // todo: check if using is only using definitions from copy members!

    if (params.size() != calledProgram.getInputParameter().size()) {
      throw new RuntimeException("TODO: correct error, param sizes dont match");
    }

    List<CobolExpressionNode> arguments = new ArrayList<>();
    for (int i = 0; i < params.size(); i++) {
      //if (params.get(i))
      // todo: check if input and caller match!
      // todo: use raw pointer only for internal calls. everything else should be
      //       either always string or the specialized version -> needs a decision
      CobolHeapPointer fromPointer = this.workingStorageHeap.getHeapPointer(params.get(i))
                                                            .asRawPointer();
      arguments.add(fromPointer);
    }

    CobolGlobalFunctionLiteralNode callNode = new CobolGlobalFunctionLiteralNode(callProgramName);
    this.cobolNodeFactory.addCall(ctx.start, callNode, arguments);

    /*
    for (CobolParser.DisplayParameterContext displayParameter : ctx.displayParameter()) {
      if (displayParameter.ID() != null) {
        CobolHeapPointer fromPointer
            = this.workingStorageHeap.getHeapPointer(displayParameter.ID().toString());
        displayArgs.add(fromPointer);
      } else if (displayParameter.STRING() != null) {
        String inputString = removeStringQuotes(displayParameter.STRING().getText());
        CobolStringLiteralNode stringConstant = new CobolStringLiteralNode(inputString);
        displayArgs.add(stringConstant);
      } else {
        throw new NotImplementedException();
      }
    }
     */

    //calledProgram.getInputParameter().get()
    //System.out.println(calledProgram.getPath());
  }

  @Override
  public void enterFunctionCallStatement(CobolParser.FunctionCallStatementContext ctx) {
    this.cobolNodeFactory.addLocalCall(ctx.start, ctx.ID().getText());
  }

  @Override
  public void enterFunctionSection(CobolParser.FunctionSectionContext ctx) {
    this.cobolNodeFactory.startSection(ctx.functionSectionStart().start);
  }

  @Override
  public void exitFunctionSection(CobolParser.FunctionSectionContext ctx) {
    this.cobolNodeFactory.finishSection(ctx.stop);
  }
}