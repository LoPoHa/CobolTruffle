package com.github.lopoha.coboltruffle;

// modeled / copied from SLException

import com.github.lopoha.coboltruffle.runtime.CobolContext;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.TruffleException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.source.SourceSection;

public class CobolException extends RuntimeException implements TruffleException {

  // TODO serialVersionUID
  private static final long serialVersionUID = 1L;
  private final Node location;

  @TruffleBoundary
  public CobolException(String message, Node location) {
    super(message);
    this.location = location;
  }

  @SuppressWarnings("sync-override")
  @Override
  public final Throwable fillInStackTrace() {
    return this;
  }

  public Node getLocation() {
    return location;
  }

  /**
   * TODO.
   * @param operation todo
   * @param values todo
   * @return todo
   */
  @TruffleBoundary
  public static CobolException typeError(Node operation, Object... values) {
    StringBuilder result = new StringBuilder();
    result.append("Type error");

    if (operation != null) {
      SourceSection ss = operation.getEncapsulatingSourceSection();
      if (ss != null && ss.isAvailable()) {
        result.append(" at ")
              .append(ss.getSource().getName())
              .append(" line ")
              .append(ss.getStartLine())
              .append(" col ")
              .append(ss.getStartColumn());
      }
    }

    result.append(": operation");
    if (operation != null) {
      NodeInfo nodeInfo = CobolContext.lookupNodeInfo(operation.getClass());
      if (nodeInfo != null) {
        result.append(" \"").append(nodeInfo.shortName()).append("\"");
      }
    }

    result.append(" not defined for");

    String sep = " ";
    for (Object value : values) {
      result.append(sep);
      sep = ", ";
      if (value == null || InteropLibrary.getFactory().getUncached().isNull(value)) {
        result.append(CobolLanguage.toString(value));
      } else {
        result.append(CobolLanguage.getMetaObject(value));
        result.append(" ");
        if (InteropLibrary.getFactory().getUncached().isString(value)) {
          result.append("\"");
        }
        result.append(CobolLanguage.toString(value));
        if (InteropLibrary.getFactory().getUncached().isString(value)) {
          result.append("\"");
        }
      }
    }
    return new CobolException(result.toString(), operation);
  }

}