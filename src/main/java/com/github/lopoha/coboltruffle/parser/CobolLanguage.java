package com.github.lopoha.coboltruffle.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.github.lopoha.coboltruffle.parser.builtins.CobolBuiltinNode;
import com.github.lopoha.coboltruffle.parser.common.ParserSettings;
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
/*
import com.oracle.truffle.sl.builtins.SLBuiltinNode;
import com.oracle.truffle.sl.builtins.SLDefineFunctionBuiltin;
import com.oracle.truffle.sl.builtins.SLNanoTimeBuiltin;
import com.oracle.truffle.sl.builtins.SLPrintlnBuiltin;
import com.oracle.truffle.sl.builtins.SLReadlnBuiltin;
import com.oracle.truffle.sl.builtins.SLStackTraceBuiltin;
import com.oracle.truffle.sl.nodes.SLEvalRootNode;
import com.oracle.truffle.sl.nodes.SLTypes;
import com.oracle.truffle.sl.nodes.controlflow.SLBlockNode;
import com.oracle.truffle.sl.nodes.controlflow.SLBreakNode;
import com.oracle.truffle.sl.nodes.controlflow.SLContinueNode;
import com.oracle.truffle.sl.nodes.controlflow.SLDebuggerNode;
import com.oracle.truffle.sl.nodes.controlflow.SLIfNode;
import com.oracle.truffle.sl.nodes.controlflow.SLReturnNode;
import com.oracle.truffle.sl.nodes.controlflow.SLWhileNode;
import com.oracle.truffle.sl.nodes.expression.SLAddNode;
import com.oracle.truffle.sl.nodes.expression.SLBigIntegerLiteralNode;
import com.oracle.truffle.sl.nodes.expression.SLDivNode;
import com.oracle.truffle.sl.nodes.expression.SLEqualNode;
import com.oracle.truffle.sl.nodes.expression.SLFunctionLiteralNode;
import com.oracle.truffle.sl.nodes.expression.SLInvokeNode;
import com.oracle.truffle.sl.nodes.expression.SLLessOrEqualNode;
import com.oracle.truffle.sl.nodes.expression.SLLessThanNode;
import com.oracle.truffle.sl.nodes.expression.SLLogicalAndNode;
import com.oracle.truffle.sl.nodes.expression.SLLogicalOrNode;
import com.oracle.truffle.sl.nodes.expression.SLMulNode;
import com.oracle.truffle.sl.nodes.expression.SLReadPropertyNode;
import com.oracle.truffle.sl.nodes.expression.SLStringLiteralNode;
import com.oracle.truffle.sl.nodes.expression.SLSubNode;
import com.oracle.truffle.sl.nodes.expression.SLWritePropertyNode;
import com.oracle.truffle.sl.nodes.local.SLLexicalScope;
import com.oracle.truffle.sl.nodes.local.SLReadLocalVariableNode;
import com.oracle.truffle.sl.nodes.local.SLWriteLocalVariableNode;
import com.oracle.truffle.sl.parser.SLNodeFactory;
import com.oracle.truffle.sl.parser.SimpleLanguageLexer;
import com.oracle.truffle.sl.parser.SimpleLanguageParser;
import com.oracle.truffle.sl.runtime.SLBigNumber;
import com.oracle.truffle.sl.runtime.SLContext;
import com.oracle.truffle.sl.runtime.SLFunction;
import com.oracle.truffle.sl.runtime.SLFunctionRegistry;
import com.oracle.truffle.sl.runtime.SLNull;
*/

@TruffleLanguage.Registration(id = CobolLanguage.ID, name = "Cobol", defaultMimeType = CobolLanguage.MIME_TYPE, characterMimeTypes = CobolLanguage.MIME_TYPE, contextPolicy = ContextPolicy.SHARED)
/*
// todo: check each tag...
@ProvidedTags({StandardTags.CallTag.class, StandardTags.StatementTag.class, StandardTags.RootTag.class, StandardTags.RootBodyTag.class, StandardTags.ExpressionTag.class, DebuggerTags.AlwaysHalt.class,
                StandardTags.ReadVariableTag.class, StandardTags.WriteVariableTag.class})
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
        String preprocessed = new Temp().demo_getPreprocessedString("test", parserSettings);

        Map<String, RootCallTarget> functions = new Temp().demo_processPreprocessed(preprocessed, this);
        RootCallTarget main = functions.get("main");

        if (main == null) {
            main.getRootNode();
        }
        for(String key : functions.keySet()) {
            System.out.println("Found key " + key);
        }
        RootNode evalMain = new CobolEvalRootNode(this, main, functions);
        return Truffle.getRuntime().createCallTarget(evalMain);
    }

    /*
     * Still necessary for the old SL TCK to pass. We should remove with the old TCK. New language
     * should not override this.
     */
    @SuppressWarnings("deprecation")
    @Override
    protected Object findExportedSymbol(CobolContext context, String globalName, boolean onlyExplicit) {
        //return context.getFunctionRegistry().lookup(globalName, false);
        throw new NotImplementedException();
    }

    @Override
    protected boolean isVisible(CobolContext context, Object value) {
        return !InteropLibrary.getFactory().getUncached(value).isNull(value);
    }

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
                Scope vscope = Scope.newBuilder(nextScope.getName(), nextScope.getVariables(frame)).node(nextScope.getNode()).arguments(nextScope.getArguments(frame)).rootInstance(
                                functionObject).build();
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

    private static final List<NodeFactory<? extends CobolBuiltinNode>> EXTERNAL_BUILTINS = Collections.synchronizedList(new ArrayList<>());

    public static void installBuiltin(NodeFactory<? extends CobolBuiltinNode> builtin) {
        EXTERNAL_BUILTINS.add(builtin);
    }

}