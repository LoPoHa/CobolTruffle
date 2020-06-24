package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.parser.antlr.*;
import java.util.Map;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.RootNode;

public final class CobolEvalRootNode extends RootNode {

    private final Map<String, RootCallTarget> functions;
    @CompilationFinal private boolean registered;

    @Child private DirectCallNode mainCallNode;

    public CobolEvalRootNode(CobolLanguage language, RootCallTarget rootFunction, Map<String, RootCallTarget> functions) {
        super(language);
        this.functions = functions;
        this.mainCallNode = rootFunction != null ? DirectCallNode.create(rootFunction) : null;
    }

    @Override
    public boolean isInternal() {
        return true;
    }

    @Override
    protected boolean isInstrumentable() {
        return false;
    }

    @Override
    public String getName() {
        return "root eval";
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public Object execute(VirtualFrame frame) {
        /* Lazy registrations of functions on first execution. */
        if (!registered) {
            /* Function registration is a slow-path operation that must not be compiled. */
            CompilerDirectives.transferToInterpreterAndInvalidate();
            //lookupContextReference(CobolLanguage.class).get().getFunctionRegistry().register(functions);
            registered = true;
            // todo
            //throw new NotImplementedException();
        }

        // TODO: should this be null every time?
        return CobolNull.SINGLETON;
    }
}
