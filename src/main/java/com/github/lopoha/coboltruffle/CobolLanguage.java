package com.github.lopoha.coboltruffle;

import com.github.lopoha.coboltruffle.builtins.CobolBuiltinNode;
import com.github.lopoha.coboltruffle.builtins.CobolDisplayBuiltinFactory;
import com.github.lopoha.coboltruffle.heap.CobolHeap;
import com.github.lopoha.coboltruffle.heap.HeapBuilder;
import com.github.lopoha.coboltruffle.heap.HeapPointer;
import com.github.lopoha.coboltruffle.nodes.CobolEvalRootNode;
import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.nodes.local.CobolLexicalScope;
import com.github.lopoha.coboltruffle.nodes.local.CobolReadArgumentNode;
import com.github.lopoha.coboltruffle.parser.CobolMainParser;
import com.github.lopoha.coboltruffle.parser.ParserSettings;
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
import com.oracle.truffle.api.source.Source;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


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
  private final CobolHeap heap = new CobolHeap();
  // todo what should happen if a name is there multiple times?
  private static final Map<String, CobolBuiltinNode> builtins
      = Collections.synchronizedMap(new HashMap<>());
  private static boolean addedInternalBuiltins = false;
  private static final List<String> copySearchPath
      = Collections.synchronizedList(new ArrayList<>());
  private static final List<String> programSearchPath
      = Collections.synchronizedList(new ArrayList<>());

  /**
   * TODO.
   */
  public CobolLanguage() {
    if (!addedInternalBuiltins) {
      CobolLanguage.installBuiltin(CobolDisplayBuiltinFactory.getInstance());
      addedInternalBuiltins = true;
    }
  }

  /**
   * Returns a list with all the builtin function names.
   * @return the list with all the builtin function names.
   */
  public static List<String> getBuiltinFunctionNames() {
    return new ArrayList<>(CobolLanguage.builtins.keySet());
  }

  /**
   * Add a HeapBuilder to the heap.
   * TODO: Should only one heapbuilder be allowed to be added?
   * @param heapBuilder The heapbuilder to add.
   */
  public void addToHeap(HeapBuilder heapBuilder) {
    heap.addToHeap(heapBuilder);
  }

  public HeapPointer heapGetVariable(String name) {
    return this.heap.getHeapPointer(name);
  }

  @Override
  public CobolContext createContext(Env env) {
    return new CobolContext(this, env, builtins);
  }

  private void addRelativeToPath(Source source,
                                 List<String> programSearchPath,
                                 List<String> copySearchPath) {
    if (source.getPath() != null) {
      Path path = Paths.get(source.getPath());
      Path parent = path.getParent();
      if (parent != null) {
        programSearchPath.add(parent.toString());
        // if the source file was at the root, there is no grandparent...
        // e.g. /test.cbl or C:/test.cbl -> no way there is a copy folder "above" that.
        Path grandParent = parent.getParent();
        if (grandParent != null) {
          copySearchPath.add(grandParent.toString() + "/copy");
        }
      }
    }
  }

  @Override
  protected CallTarget parse(ParsingRequest request) {
    Source source = request.getSource();
    // todo: should a repl be allowed? would be a fun experiment...
    List<String> programSearchPath = new ArrayList<>(CobolLanguage.programSearchPath);
    List<String> copySearchPath = new ArrayList<>(CobolLanguage.copySearchPath);
    addRelativeToPath(source, programSearchPath, copySearchPath);
    ParserSettings parserSettings = new ParserSettings(copySearchPath, programSearchPath);
    // programName and filename must be the same!
    Map<String, RootCallTarget> functions
        = CobolMainParser.processSource(source, this, parserSettings);
    // todo: if repl is allowed, this doesn't work anymore
    String fileName = getFilenameWithoutExtension(source.getName());
    RootCallTarget main = functions.get(fileName);

    if (main == null) {
      throw new NotImplementedException();
    }

    RootNode evalMain = new CobolEvalRootNode(this, main, functions);
    return Truffle.getRuntime().createCallTarget(evalMain);
  }

  private String getFilenameWithoutExtension(String name) {
    int pos = name.lastIndexOf(".");
    if (pos > 0) {
      name = name.substring(0, pos);
    }
    return name;
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

  /**
   * add a new builtin.
   * @param builtin the builtin to add.
   */
  public static void installBuiltin(NodeFactory<? extends CobolBuiltinNode> builtin) {
    CobolBuiltinNode cobolBuiltinNode
        = createBuiltinNode(CobolDisplayBuiltinFactory.getInstance());
    String name = CobolContext.lookupNodeInfo(cobolBuiltinNode.getClass()).shortName();
    CobolLanguage.builtins.put(name, cobolBuiltinNode);
  }

  /**
   * Add a path to be searched if a copy is included.
   * FIFO, the first path added is searched first.
   * All paths added are evaluated before the interal search.
   * This way it is possible to `overwrite` copy members.
   *
   * @param path the path to add.
   */
  public static void addCopySearchPath(String path) {
    CobolLanguage.copySearchPath.add(path);
  }

  /**
   * Add a path to be searched if a program is called.
   * FIFO, the first path added is searched first.
   * All paths added are evaluated before the interal search.
   * This way it is possible to `overwrite` programs.
   *
   * @param path the path to add.
   */
  public static void addProgramSearchPath(String path) {
    CobolLanguage.programSearchPath.add(path);
  }

  private static CobolBuiltinNode createBuiltinNode(
      NodeFactory<? extends CobolBuiltinNode> factory) {
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
    builtinBodyNode.setUnavailableSourceSection();

    return builtinBodyNode;
  }

}