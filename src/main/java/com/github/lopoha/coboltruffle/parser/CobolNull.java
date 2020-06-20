package com.github.lopoha.coboltruffle.parser;

// modeled / copied from CobolException

import com.github.lopoha.coboltruffle.parser.antlr.*;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(InteropLibrary.class)
@SuppressWarnings("static-method")
public final class CobolNull implements TruffleObject {

    /**
     * The canonical value to represent {@code null} in Cobol.
     */
    public static final CobolNull SINGLETON = new CobolNull();

    /**
     * Disallow instantiation from outside to ensure that the {@link #SINGLETON} is the only
     * instance.
     */
    private CobolNull() {
    }

    /**
     * This method is, e.g., called when using the {@code null} value in a string concatenation. So
     * changing it has an effect on Cobol programs.
     */
    @Override
    public String toString() {
        return "NULL";
    }

    /**
     * {@link CobolNull} values are interpreted as null values by other languages.
     */
    @ExportMessage
    boolean isNull() {
        return true;
    }
}
