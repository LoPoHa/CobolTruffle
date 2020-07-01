package com.github.lopoha.coboltruffle.nodes.util;

import com.github.lopoha.coboltruffle.nodes.CobolExpressionNode;
import com.github.lopoha.coboltruffle.nodes.CobolTypes;
import com.github.lopoha.coboltruffle.runtime.CobolNull;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;

/**
 * From SimpleLanguage.
 * The node to normalize any value to an Cobol value. This is useful to reduce the number of values
 * expression nodes need to expect.
 */
@TypeSystemReference(CobolTypes.class)
@NodeChild
public abstract class CobolUnboxNode extends CobolExpressionNode {

  static final int LIMIT = 5;

  @Specialization
  protected static String fromString(String value) {
    return value;
  }

  @Specialization
  protected static boolean fromBoolean(boolean value) {
    return value;
  }

  @Specialization
  protected static CobolNull fromFunction(CobolNull value) {
    return value;
  }

  /**
   * TODO.
   * @param value todo
   * @param interop todo
   * @return todo
   */
  @Specialization(limit = "LIMIT")
  public static Object fromForeign(Object value, @CachedLibrary("value") InteropLibrary interop) {
    try {
      if (interop.isString(value)) {
        return interop.asString(value);
      } else if (interop.isBoolean(value)) {
        return interop.asBoolean(value);
      } else {
        return value;
      }
    } catch (UnsupportedMessageException e) {
      CompilerDirectives.transferToInterpreter();
      throw new AssertionError();
    }
  }

}
