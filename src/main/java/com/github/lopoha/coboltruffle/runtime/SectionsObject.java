package com.github.lopoha.coboltruffle.runtime;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.util.HashMap;
import java.util.Map;

@ExportLibrary(InteropLibrary.class)
@SuppressWarnings("static-method")
final class SectionsObject implements TruffleObject {

  final Map<String, CobolSection> functions = new HashMap<>();

  SectionsObject() {
  }

  public static boolean isInstance(TruffleObject obj) {
    return obj instanceof SectionsObject;
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
  boolean hasMembers() {
    return true;
  }

  @ExportMessage
  @TruffleBoundary
  Object readMember(String member) {
    return functions.get(member);
  }

  @ExportMessage
  @TruffleBoundary
  boolean isMemberReadable(String member) {
    return functions.containsKey(member);
  }

  @ExportMessage
  @TruffleBoundary
  Object getMembers(@SuppressWarnings("unused") boolean includeInternal) {
    return new FunctionNamesObject(functions.keySet().toArray());
  }

  @ExportMessage
  boolean hasMetaObject() {
    return true;
  }

  @ExportMessage
  Object getMetaObject() {
    // todo
    return CobolType.NULL;
  }

  @ExportMessage
  @TruffleBoundary
  Object toDisplayString(@SuppressWarnings("unused") boolean allowSideEffects) {
    return functions.toString();
  }

  @ExportLibrary(InteropLibrary.class)
  static final class FunctionNamesObject implements TruffleObject {

    private final Object[] names;

    FunctionNamesObject(Object[] names) {
      this.names = names;
    }

    @ExportMessage
    boolean hasArrayElements() {
      return true;
    }

    @ExportMessage
    boolean isArrayElementReadable(long index) {
      return index >= 0 && index < names.length;
    }

    @ExportMessage
    long getArraySize() {
      return names.length;
    }

    @ExportMessage
    Object readArrayElement(long index) throws InvalidArrayIndexException {
      if (!isArrayElementReadable(index)) {
        CompilerDirectives.transferToInterpreter();
        throw InvalidArrayIndexException.create(index);
      }
      return names[(int) index];
    }
  }
}
