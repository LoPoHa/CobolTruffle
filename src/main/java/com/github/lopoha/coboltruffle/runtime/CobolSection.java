package com.github.lopoha.coboltruffle.runtime;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.github.lopoha.coboltruffle.nodes.CobolUndefinedSectionRootNode;
import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLogger;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ReportPolymorphism;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.utilities.CyclicAssumption;
import java.util.logging.Level;

@ExportLibrary(InteropLibrary.class)
@SuppressWarnings("static-method")
public final class CobolSection implements TruffleObject {

  public static final int INLINE_CACHE_SIZE = 2;

  private static final TruffleLogger LOG =
      TruffleLogger.getLogger(CobolLanguage.ID, CobolSection.class);

  private final String name;
  private final CyclicAssumption callTargetStable;
  private RootCallTarget callTarget;

  protected CobolSection(CobolLanguage language, String name) {
    this.name = name;
    this.callTarget =
        Truffle.getRuntime().createCallTarget(new CobolUndefinedSectionRootNode(language, name));
    this.callTargetStable = new CyclicAssumption(name);
  }

  public String getName() {
    return name;
  }

  public RootCallTarget getCallTarget() {
    return callTarget;
  }

  protected void setCallTarget(RootCallTarget callTarget) {
    this.callTarget = callTarget;
    LOG.log(Level.FINE, "Installed call target for: {0}", name);
    callTargetStable.invalidate();
  }

  public Assumption getCallTargetStable() {
    return callTargetStable.getAssumption();
  }

  @Override
  public String toString() {
    return name;
  }

  @ExportMessage
  boolean hasLanguage() {
    return true;
  }

  @ExportMessage
  Class<? extends TruffleLanguage<?>> getLanguage() {
    return CobolLanguage.class;
  }

  @SuppressWarnings("static-method")
  @ExportMessage
  @TruffleBoundary
  SourceSection getSourceLocation() {
    return getCallTarget().getRootNode().getSourceSection();
  }

  @SuppressWarnings("static-method")
  @ExportMessage
  boolean hasSourceLocation() {
    return true;
  }

  @ExportMessage
  boolean isExecutable() {
    return true;
  }

  @ExportMessage
  boolean hasMetaObject() {
    return true;
  }

  @ExportMessage
  Object getMetaObject() {
    return CobolType.SECTION;
  }

  @ExportMessage
  Object toDisplayString(@SuppressWarnings("unused") boolean allowSideEffects) {
    return name;
  }

  @ReportPolymorphism
  @ExportMessage
  abstract static class Execute {
    @Specialization(
        limit = "INLINE_CACHE_SIZE",
        guards = "function.getCallTarget() == cachedTarget", //
        assumptions = "callTargetStable")
    @SuppressWarnings("unused")
    protected static Object doDirect(
        CobolSection function,
        Object[] arguments,
        @Cached("function.getCallTargetStable()") Assumption callTargetStable,
        @Cached("function.getCallTarget()") RootCallTarget cachedTarget,
        @Cached("create(cachedTarget)") DirectCallNode callNode) {
      return callNode.call(arguments);
    }

    @Specialization(replaces = "doDirect")
    protected static Object doIndirect(
        CobolSection function, Object[] arguments, @Cached IndirectCallNode callNode) {
      return callNode.call(function.getCallTarget(), arguments);
    }
  }
}
