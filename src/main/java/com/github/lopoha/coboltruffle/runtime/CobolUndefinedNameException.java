package com.github.lopoha.coboltruffle.runtime;

import com.github.lopoha.coboltruffle.CobolException;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.nodes.Node;

public final class CobolUndefinedNameException extends CobolException {
  private static final long serialVersionUID = 1L;

  @TruffleBoundary
  public static CobolUndefinedNameException undefinedFunction(Node location, Object name) {
    throw new CobolUndefinedNameException("Undefined function: " + name, location);
  }

  @TruffleBoundary
  public static CobolUndefinedNameException undefinedProperty(Node location, Object name) {
    throw new CobolUndefinedNameException("Undefined property: " + name, location);
  }

  private CobolUndefinedNameException(String message, Node node) {
    super(message, node);
  }
}
