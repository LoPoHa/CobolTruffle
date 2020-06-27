package com.github.lopoha.coboltruffle;

import com.github.lopoha.coboltruffle.builtins.CobolBuiltinNode;
import com.github.lopoha.coboltruffle.heap.CobolHeap;
import com.github.lopoha.coboltruffle.heap.HeapPointer;
import com.github.lopoha.coboltruffle.nodes.CobolEvalRootNode;
import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.nodes.CobolMoveNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolFunctionLiteralNode;
import com.github.lopoha.coboltruffle.nodes.expression.CobolStringLiteralNode;
import com.github.lopoha.coboltruffle.nodes.local.CobolLexicalScope;
import com.github.lopoha.coboltruffle.parser.CobolBaseListenerImpl;
import com.github.lopoha.coboltruffle.parser.CobolNodeFactory;
import com.github.lopoha.coboltruffle.parser.NotImplementedException;
import com.github.lopoha.coboltruffle.parser.antlr.CobolLexer;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser;
import com.github.lopoha.coboltruffle.parser.common.ParserSettings;
import com.github.lopoha.coboltruffle.parser.preprocessor.ParserPreprocessor;
import com.github.lopoha.coboltruffle.runtime.CobolContext;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Scope;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLanguage.ContextPolicy;
import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


@TruffleLanguage.Registration(id = CobolLanguage.ID,
                              name = "Cobol",
                              defaultMimeType = CobolLanguage.MIME_TYPE,
                              characterMimeTypes = CobolLanguage.MIME_TYPE,
                              contextPolicy = ContextPolicy.SHARED)
/*
// todo: check each tag...
@ProvidedTags({StandardTags.CallTag.class,
               StandardTags.StatementTag.class,
               StandardTags.RootTag.class,
               StandardTags.RootBodyTag.class,
               StandardTags.ExpressionTag.class,
               DebuggerTags.AlwaysHalt.class,
               StandardTags.ReadVariableTag.class,
               StandardTags.WriteVariableTag.class})
 */
public final class CobolLanguage extends TruffleLanguage<CobolContext> {
  public static final String ID = "Cobol";
  public static final String MIME_TYPE = "application/x-cbl";

  @Override
  public CobolContext createContext(Env env) {
    return new CobolContext(this, env);
  }

  @Override
  protected CallTarget parse(ParsingRequest request) throws Exception {
    //Source source = request.getSource();
    List<String> programSearchPath = new ArrayList<>();
    programSearchPath.add("./teststuff/program");
    List<String> copySearchPath = new ArrayList<>();
    copySearchPath.add("./teststuff/copy");
    ParserSettings parserSettings = new ParserSettings(copySearchPath, programSearchPath);
    String preprocessed = ParserPreprocessor.getPreprocessedString("test", parserSettings);

    Map<String, RootCallTarget> functions = processPreprocessed(preprocessed, this);
    RootCallTarget main = functions.get("main");

    if (main == null) {
      // todo
      main.getRootNode();
    }

    RootNode evalMain = new CobolEvalRootNode(this, main, functions);
    return Truffle.getRuntime().createCallTarget(evalMain);
  }

  /**
   * TODO: replace method.
   * @param source the preprocessed source code.
   * @param cobolLanguage a reference to the cobollanguage.
   * @return a map with all functions.
   */
  public Map<String, RootCallTarget> processPreprocessed(String source,
                                                              CobolLanguage cobolLanguage) {
    try {
      CharStream input = CharStreams.fromString(source);
      CobolLexer lexer = new CobolLexer(input);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      CobolParser parser = new CobolParser(tokens);
      CobolParser.FileContext fileContext = parser.file();
      ParseTreeWalker walker = new ParseTreeWalker();
      CobolBaseListenerImpl listener = new CobolBaseListenerImpl();
      walker.walk(listener, fileContext);

      CobolHeap workingStorageHeap = new CobolHeap();
      workingStorageHeap.addToHeap(listener.workingStorageHeap);



      CobolNodeFactory cobolNodeFactory = new CobolNodeFactory(cobolLanguage);
      cobolNodeFactory.startSection("main");

      CobolStringLiteralNode stringConstant = new CobolStringLiteralNode("hello World");
      CobolFunctionLiteralNode println = new CobolFunctionLiteralNode("display");
      List<CobolExpressionNode> printlnArgs = new ArrayList<>();
      printlnArgs.add(stringConstant);
      cobolNodeFactory.addCall(println, printlnArgs);

      HeapPointer programName = workingStorageHeap.getHeapPointer("PROGRAMNAME");
      List<CobolExpressionNode> println2Args = new ArrayList<>();
      println2Args.add(programName);
      cobolNodeFactory.addCall(println, println2Args);

      CobolMoveNode moveNode = new CobolMoveNode("ABC", programName);
      cobolNodeFactory.addMove(moveNode);
      cobolNodeFactory.addCall(println, println2Args);

      HeapPointer copyString = workingStorageHeap.getHeapPointer("COPY-STRING");
      CobolMoveNode moveNode2 = new CobolMoveNode(copyString, programName);
      cobolNodeFactory.addMove(moveNode2);
      cobolNodeFactory.addCall(println, println2Args);

      cobolNodeFactory.finishSection();
      return cobolNodeFactory.getAllSections();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /*
   * Still necessary for the old SL TCK to pass. We should remove with the old TCK. New language
   * should not override this.
   */
  @SuppressWarnings("deprecation")
  @Override
  protected Object findExportedSymbol(CobolContext context,
                                      String globalName,
                                      boolean onlyExplicit) {
    //return context.getFunctionRegistry().lookup(globalName, false);
    throw new NotImplementedException();
  }

  @Override
  protected boolean isVisible(CobolContext context, Object value) {
    return !InteropLibrary.getFactory().getUncached(value).isNull(value);
  }

  /**
   * todo.
   * @param value todo
   * @return todo
   */
  public static String toString(Object value) {
    try {
      if (value == null) {
        return "ANY";
      }
      InteropLibrary interop = InteropLibrary.getFactory().getUncached(value);
      if (interop.fitsInLong(value)) {
        return Long.toString(interop.asLong(value));
      } else if (interop.isBoolean(value)) {
        return Boolean.toString(interop.asBoolean(value));
      } else if (interop.isString(value)) {
        return interop.asString(value);
      } else if (interop.isNull(value)) {
        return "NULL";
      } else if (interop.isExecutable(value)) {
        throw new NotImplementedException();
        /*
        if (value instanceof SLFunction) {
          return ((SLFunction) value).getName();
        } else {
           return "Function";
        }
        */
      } else if (interop.hasMembers(value)) {
        return "Object";
      } else {
        return "Unsupported";
      }
    } catch (UnsupportedMessageException e) {
      CompilerDirectives.transferToInterpreter();
      throw new AssertionError();
    }
  }

  /**
   * TODO.
   * @param value todo
   * @return todo
   */
  public static String getMetaObject(Object value) {
    return "ANY";
    /*
    if (value == null) {
        return "ANY";
    }
    InteropLibrary interop = InteropLibrary.getFactory().getUncached(value);
    if (interop.isNumber(value) || value instanceof SLBigNumber) {
        return "Number";
    } else if (interop.isBoolean(value)) {
        return "Boolean";
    } else if (interop.isString(value)) {
        return "String";
    } else if (interop.isNull(value)) {
        return "NULL";
    } else if (interop.isExecutable(value)) {
        return "Function";
    } else if (interop.hasMembers(value)) {
        return "Object";
    } else {
        return "Unsupported";
    }
    */
  }

  @Override
  public Iterable<Scope> findLocalScopes(CobolContext context, Node node, Frame frame) {
    final CobolLexicalScope scope = CobolLexicalScope.createScope(node);
    return () -> new Iterator<>() {
      private CobolLexicalScope previousScope;
      private CobolLexicalScope nextScope = scope;

      @Override
      public boolean hasNext() {
        if (nextScope == null) {
          nextScope = previousScope.findParent();
        }
        return nextScope != null;
      }

      @Override
      public Scope next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        Object functionObject = findFunctionObject();
        Scope vscope = Scope.newBuilder(nextScope.getName(), nextScope.getVariables(frame))
                            .node(nextScope.getNode())
                            .arguments(nextScope.getArguments(frame))
                            .rootInstance(functionObject).build();
        previousScope = nextScope;
        nextScope = null;
        return vscope;
      }

      private Object findFunctionObject() {
        String name = node.getRootNode().getName();
        //return context.getFunctionRegistry().getFunction(name);
        throw new NotImplementedException();
      }
    };
  }

  @Override
  protected Iterable<Scope> findTopScopes(CobolContext context) {
    return context.getTopScopes();
  }

  public static CobolContext getCurrentContext() {
    return getCurrentContext(CobolLanguage.class);
  }

  private static final List<NodeFactory<? extends CobolBuiltinNode>> EXTERNAL_BUILTINS
      = Collections.synchronizedList(new ArrayList<>());

  public static void installBuiltin(NodeFactory<? extends CobolBuiltinNode> builtin) {
    EXTERNAL_BUILTINS.add(builtin);
  }

}