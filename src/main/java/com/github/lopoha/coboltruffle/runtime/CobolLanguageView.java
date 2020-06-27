package com.github.lopoha.coboltruffle.runtime;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.ExplodeLoop;

/**
 * TODO.
 */
@ExportLibrary(value = InteropLibrary.class, delegateTo = "delegate")
@SuppressWarnings("static-method")
public final class CobolLanguageView implements TruffleObject {

  final Object delegate;

  CobolLanguageView(Object delegate) {
    this.delegate = delegate;
  }

  @ExportMessage
  boolean hasLanguage() {
    return true;
  }

  @ExportMessage
  Class<? extends TruffleLanguage<?>> getLanguage() {
    return CobolLanguage.class;
  }

  @ExportMessage
  @ExplodeLoop
  boolean hasMetaObject(@CachedLibrary("this.delegate") InteropLibrary interop) {
    for (CobolType type : CobolType.PRECEDENCE) {
      if (type.isInstance(delegate, interop)) {
        return true;
      }
    }
    return false;
  }

  @ExportMessage
  @ExplodeLoop
  Object getMetaObject(@CachedLibrary("this.delegate") InteropLibrary interop)
      throws UnsupportedMessageException {
    for (CobolType type : CobolType.PRECEDENCE) {
      if (type.isInstance(delegate, interop)) {
        return type;
      }
    }
    throw UnsupportedMessageException.create();
  }

  @ExportMessage
  @ExplodeLoop
  Object toDisplayString(@SuppressWarnings("unused") boolean allowSideEffects,
                         @CachedLibrary("this.delegate") InteropLibrary interop) {
    for (CobolType type : CobolType.PRECEDENCE) {
      if (type.isInstance(this.delegate, interop)) {
        try {
          if (type == CobolType.STRING) {
            return interop.asString(delegate);
          } else {
            /* We use the type name as fallback for any other type */
            return type.getName();
          }
        } catch (UnsupportedMessageException e) {
          CompilerDirectives.transferToInterpreter();
          throw new AssertionError();
        }
      }
    }
    return "Unsupported";
  }

  /*
   * Long.toString is not safe for partial evaluation and therefore needs to be called behind a
   * boundary.
   */
  @TruffleBoundary
  private static String longToString(long l) {
    return Long.toString(l);
  }

  public static Object create(Object value) {
    assert isPrimitiveOrFromOtherLanguage(value);
    return new CobolLanguageView(value);
  }

  /*
   * Language views are intended to be used only for primitives and other language values.
   */
  private static boolean isPrimitiveOrFromOtherLanguage(Object value) {
    InteropLibrary interop = InteropLibrary.getFactory().getUncached(value);
    try {
      return !interop.hasLanguage(value) || interop.getLanguage(value) != CobolLanguage.class;
    } catch (UnsupportedMessageException e) {
      CompilerDirectives.transferToInterpreter();
      throw new AssertionError(e);
    }
  }

  /**
   * Returns a language view for primitive or foreign values. Returns the same value for values
   * that are already originating from SimpleLanguage. This is useful to view values from the
   * perspective of simple language in slow paths, for example, printing values in error messages.
   */
  @TruffleBoundary
  public static Object forValue(Object value) {
    if (value == null) {
      return null;
    }
    InteropLibrary lib = InteropLibrary.getFactory().getUncached(value);
    try {
      if (lib.hasLanguage(value) && lib.getLanguage(value) == CobolLanguage.class) {
        return value;
      } else {
        return create(value);
      }
    } catch (UnsupportedMessageException e) {
      CompilerDirectives.transferToInterpreter();
      throw new AssertionError(e);
    }
  }

}
