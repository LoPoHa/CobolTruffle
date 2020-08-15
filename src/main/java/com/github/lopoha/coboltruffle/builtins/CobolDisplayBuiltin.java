package com.github.lopoha.coboltruffle.builtins;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.github.lopoha.coboltruffle.runtime.CobolContext;
import com.github.lopoha.coboltruffle.runtime.CobolLanguageView;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.NodeInfo;

// todo: allow multiple strings as inputs...
@NodeInfo(shortName = "display")
public abstract class CobolDisplayBuiltin extends CobolBuiltinNode {

  /**
   * The display builtin function. Like a println in other languages.
   *
   * @param value the value to print.
   * @param interop todo.
   * @param context todo.
   * @return todo. why does this return something else than null?
   */
  @Specialization
  @TruffleBoundary
  public Object println(
      Object value,
      @CachedLibrary(limit = "3") InteropLibrary interop,
      @CachedContext(CobolLanguage.class) CobolContext context) {
    context.getOutput().println(interop.toDisplayString(CobolLanguageView.forValue(value)));
    return value;
  }
}
