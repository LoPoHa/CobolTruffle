package com.github.lopoha.coboltruffle.runtime;

import com.github.lopoha.coboltruffle.parser.CobolLanguage;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(InteropLibrary.class)
@SuppressWarnings("static-method")
public final class CobolType implements TruffleObject {

  /*
   * todo: replace stole sllanguage comment
   * These are the sets of builtin types in simple languages. In case of simple language the types
   * nicely match those of the types in InteropLibrary. This might not be the case and more
   * additional checks need to be performed (similar to number checking for SLBigNumber).
   */
  public static final CobolType NULL = new CobolType("NULL", InteropLibrary::isNull);
  public static final CobolType STRING = new CobolType("String", InteropLibrary::isString);
  public static final CobolType SECTION = new CobolType("Section", InteropLibrary::isExecutable);

  /*
   * todo: replace stole sllanguage comment
   * This array is used when all types need to be checked in a certain order. While most interop
   * types like number or string are exclusive, others traits like members might not be. For
   * example, an object might be a function. In SimpleLanguage we decided to make functions,
   * functions and not objects.
   */
  @CompilationFinal(dimensions = 1)
  public static final CobolType[] PRECEDENCE = new CobolType[]{NULL, STRING, SECTION};

  private final String name;
  private final TypeCheck isInstance;

  private CobolType(String name, TypeCheck isInstance) {
    this.name = name;
    this.isInstance = isInstance;
  }

  public boolean isInstance(Object value, InteropLibrary interop) {
    CompilerAsserts.partialEvaluationConstant(this);
    return isInstance.check(interop, value);
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
  boolean isMetaObject() {
    return true;
  }

  @ExportMessage(name = "getMetaQualifiedName")
  @ExportMessage(name = "getMetaSimpleName")
  public Object getName() {
    return name;
  }

  @ExportMessage(name = "toDisplayString")
  Object toDisplayString(@SuppressWarnings("unused") boolean allowSideEffects) {
    return name;
  }

  @Override
  public String toString() {
    return "CobolType[" + name + "]";
  }

  @ExportMessage
  static class IsMetaInstance {

    @Specialization(guards = "type == cachedType", limit = "3")
    static boolean doCached(@SuppressWarnings("unused") CobolType type,
                            Object value,
                            @Cached("type") CobolType cachedType,
                            @CachedLibrary("value") InteropLibrary valueLib) {
      return cachedType.isInstance.check(valueLib, value);
    }

    @TruffleBoundary
    @Specialization(replaces = "doCached")
    static boolean doGeneric(CobolType type, Object value) {
      return type.isInstance.check(InteropLibrary.getFactory().getUncached(), value);
    }
  }

  @FunctionalInterface
  interface TypeCheck {
    boolean check(InteropLibrary lib, Object value);
  }

}
