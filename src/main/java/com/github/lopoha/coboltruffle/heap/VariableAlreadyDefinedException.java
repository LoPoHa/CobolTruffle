package com.github.lopoha.coboltruffle.heap;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.TruffleException;
import com.oracle.truffle.api.nodes.Node;

public class VariableAlreadyDefinedException extends RuntimeException implements TruffleException {

  @TruffleBoundary
  public VariableAlreadyDefinedException(String variableName) {
    super(variableName);
  }

  @SuppressWarnings("sync-override")
  @Override
  public final Throwable fillInStackTrace() {
    return this;
  }

  @Override
  public Node getLocation() {
    // todo
    return null;
  }
}
