package com.github.lopoha.coboltruffle.runtime;

// modeled / copied from SL

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(InteropLibrary.class)
@SuppressWarnings("static-method")
public final class CobolNull implements TruffleObject {

  /**
   * todo replace sl comment The canonical value to represent {@code null} in Cobol.
   */
  public static final CobolNull SINGLETON = new CobolNull();

  private CobolNull() {
  }

  /**
   * todo replace sl comment This method is, e.g., called when using the {@code null} value in a
   * string concatenation. So changing it has an effect on Cobol programs.
   */
  @Override
  public String toString() {
    return "NULL";
  }

  /**
   * {@link CobolNull} values are interpreted as null values by other languages.
   *
   * @return if it is null (always true).
   */
  @ExportMessage
  public boolean isNull() {
    return true;
  }
}
