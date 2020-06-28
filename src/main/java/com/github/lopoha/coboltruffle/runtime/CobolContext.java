package com.github.lopoha.coboltruffle.runtime;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.github.lopoha.coboltruffle.builtins.CobolBuiltinNode;
import com.github.lopoha.coboltruffle.builtins.CobolDisplayBuiltinFactory;
import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.nodes.CobolRootNode;
import com.github.lopoha.coboltruffle.nodes.local.CobolReadArgumentNode;
import com.github.lopoha.coboltruffle.parser.NotImplementedException;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.Scope;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage.Env;
import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.object.Layout;
import com.oracle.truffle.api.source.Source;
import java.io.PrintWriter;
import java.util.Collections;

public final class CobolContext {

  private static final Source BUILTIN_SOURCE
      = Source.newBuilder(CobolLanguage.ID, "", "Cobol builtin").build();
  static final Layout LAYOUT = Layout.createLayout();

  private final Env env;
  //private final BufferedReader input;
  private final PrintWriter output;
  private final CobolSectionRegistry functionRegistry;
  //private final Shape emptyShape;
  private final CobolLanguage language;
  //private final AllocationReporter allocationReporter;
  private final Iterable<Scope> topScopes; // Cache the top scopes

  /**
   * The context.
   * TODO...
   * @param language Reference to the [CobolLanguage]
   * @param env The env the engine runs in.
   */
  public CobolContext(CobolLanguage language, Env env) {
    this.env = env;
    this.output = new PrintWriter(env.out(), true);
    this.functionRegistry = new CobolSectionRegistry(language);
    this.topScopes = Collections.singleton(
        Scope.newBuilder("global", functionRegistry.getFunctionsObject()).build());
    this.language = language;
    installBuiltins();


    /*
      TODO
      for (NodeFactory<? extends SLBuiltinNode> builtin : externalBuiltins) {
        installBuiltin(builtin);
       }
      */
    //this.emptyShape = LAYOUT.createShape(SLObjectType.SINGLETON);
  }

  /**
   * Return the current Truffle environment.
   */
  public Env getEnv() {
    return env;
  }

  public PrintWriter getOutput() {
    return output;
  }

  /**
   * Returns the registry of all functions that are currently defined.
   */
  public CobolSectionRegistry getFunctionRegistry() {
    return functionRegistry;
  }

  public Iterable<Scope> getTopScopes() {
    return topScopes;
  }

  private void installBuiltins() {
    installBuiltin(CobolDisplayBuiltinFactory.getInstance());
    /*
    installBuiltin(SLReadlnBuiltinFactory.getInstance());
    installBuiltin(SLNanoTimeBuiltinFactory.getInstance());
    installBuiltin(SLDefineFunctionBuiltinFactory.getInstance());
    installBuiltin(SLStackTraceBuiltinFactory.getInstance());
    installBuiltin(SLHelloEqualsWorldBuiltinFactory.getInstance());
    installBuiltin(SLNewObjectBuiltinFactory.getInstance());
    installBuiltin(SLEvalBuiltinFactory.getInstance());
    installBuiltin(SLImportBuiltinFactory.getInstance());
    installBuiltin(SLGetSizeBuiltinFactory.getInstance());
    installBuiltin(SLHasSizeBuiltinFactory.getInstance());
    installBuiltin(SLIsExecutableBuiltinFactory.getInstance());
    installBuiltin(SLIsNullBuiltinFactory.getInstance());
    installBuiltin(SLWrapPrimitiveBuiltinFactory.getInstance());
    */
  }

  /**
   * Install a builtin function.
   * @param factory todo.
   */
  public void installBuiltin(NodeFactory<? extends CobolBuiltinNode> factory) {
    /*
     * The builtin node factory is a class that is automatically generated by the Truffle DSL.
     * The signature returned by the factory reflects the signature of the @Specialization
     *
     * methods in the builtin classes.
     */
    int argumentCount = factory.getExecutionSignature().size();
    CobolExpressionNode[] argumentNodes = new CobolExpressionNode[argumentCount];
    /*
     * Builtin functions are like normal functions, i.e., the arguments are passed in as an
     * Object[] array encapsulated in SLArguments. A SLReadArgumentNode extracts a parameter
     * from this array.
     */
    for (int i = 0; i < argumentCount; i++) {
      argumentNodes[i] = new CobolReadArgumentNode(i);
    }
    /* Instantiate the builtin node. This node performs the actual functionality. */
    CobolBuiltinNode builtinBodyNode = factory.createNode((Object) argumentNodes);
    builtinBodyNode.addRootTag();
    /* The name of the builtin function is specified via an annotation on the node class. */
    String name = lookupNodeInfo(builtinBodyNode.getClass()).shortName();
    builtinBodyNode.setUnavailableSourceSection();

    /* Wrap the builtin in a RootNode. Truffle requires all AST to start with a RootNode. */
    CobolRootNode rootNode = new CobolRootNode(language,
                                               new FrameDescriptor(),
                                               builtinBodyNode,
                                               BUILTIN_SOURCE.createUnavailableSection(),
                                               name);

    /* Register the builtin function in our function registry. */
    getFunctionRegistry().register(name, Truffle.getRuntime().createCallTarget(rootNode));
  }

  /**
   * TODO.
   * @param clazz todo
   * @return todo
   */
  public static NodeInfo lookupNodeInfo(Class<?> clazz) {
    if (clazz == null) {
      return null;
    }
    NodeInfo info = clazz.getAnnotation(NodeInfo.class);
    if (info != null) {
      return info;
    } else {
      return lookupNodeInfo(clazz.getSuperclass());
    }
  }


  /**
   * TODO.
   * @param value todo
   * @return todo
   */
  public static boolean isCobolObject(Object value) {
    /*
     * LAYOUT.getType() returns a concrete implementation class, i.e., a class that is more
     * precise than the base class DynamicObject. This makes the type check faster.
     */
    //return LAYOUT.getType().isInstance(value)
    //       && LAYOUT.getType().cast(value).getShape().getObjectType() == SLObjectType.SINGLETON;
    throw new NotImplementedException();
  }


  /**
   * TODO .
   * @param a todo
   * @return todo
   */
  public static Object fromForeignValue(Object a) {
    /*
    if (a instanceof Long
       || a instanceof SLBigNumber
       || a instanceof String
       || a instanceof Boolean) {
        return a;
    } else if (a instanceof Character) {
        return String.valueOf(a);
    } else if (a instanceof Number) {
        return fromForeignNumber(a);
    } else if (a instanceof TruffleObject) {
        return a;
    } else if (a instanceof CobolContext) {
        return a;
    }
    */
    CompilerDirectives.transferToInterpreter();
    throw new IllegalStateException(a + " is not a Truffle value");
  }

  @TruffleBoundary
  private static long fromForeignNumber(Object a) {
    return ((Number) a).longValue();
  }

  public CallTarget parse(Source source) {
    return env.parsePublic(source);
  }

  /**
   * Returns an object that contains bindings that were exported across all used languages. To
   * read or write from this object the {@link TruffleObject interop} API can be used.
   */
  public TruffleObject getPolyglotBindings() {
    return (TruffleObject) env.getPolyglotBindings();
  }

  public static CobolContext getCurrent() {
    return CobolLanguage.getCurrentContext();
  }

}