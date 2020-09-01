package com.github.lopoha.coboltruffle.runtime;

import com.github.lopoha.coboltruffle.CobolException;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.nodes.Node;

public final class CobolUndefinedNameException extends CobolException {
  private static final long serialVersionUID = 1L;

  private CobolUndefinedNameException(String message, Node node) {
    super(message, node);
  }

  @TruffleBoundary
  public static CobolUndefinedNameException undefinedFunction(Node location, Object name) {
    throw new CobolUndefinedNameException("Undefined function: " + name, location);
  }
}
