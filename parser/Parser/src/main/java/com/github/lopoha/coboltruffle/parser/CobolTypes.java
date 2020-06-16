package com.github.lopoha.coboltruffle.parser;

// modeled / copied from SimpleLanguage
import java.math.BigInteger;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.ImplicitCast;
import com.oracle.truffle.api.dsl.TypeCast;
import com.oracle.truffle.api.dsl.TypeCheck;
import com.oracle.truffle.api.dsl.TypeSystem;

/**
 * The type system of SL, as explained in {@link SLLanguage}. Based on the {@link TypeSystem}
 * annotation, the Truffle DSL generates the subclass {@link SLTypesGen} with type test and type
 * conversion methods for some types. In this class, we only cover types where the automatically
 * generated ones would not be sufficient.
 */
@TypeSystem({long.class, boolean.class})
public abstract class CobolTypes {

    /**
     * Example of a manually specified type check that replaces the automatically generated type
     * check that the Truffle DSL would generate. For {@link SLNull}, we do not need an
     * {@code instanceof} check, because we know that there is only a {@link SLNull#SINGLETON
     * singleton} instance.
     */
    @TypeCheck(CobolNull.class)
    public static boolean isCobolNull(Object value) {
        return value == CobolNull.SINGLETON;
    }

    /**
     * Example of a manually specified type cast that replaces the automatically generated type cast
     * that the Truffle DSL would generate. For {@link SLNull}, we do not need an actual cast,
     * because we know that there is only a {@link SLNull#SINGLETON singleton} instance.
     */
    @TypeCast(CobolNull.class)
    public static CobolNull asSLNull(Object value) {
        assert isCobolNull(value);
        return CobolNull.SINGLETON;
    }

    /**
     * Informs the Truffle DSL that a primitive {@code long} value can be used in all
     * specializations where a {@link SLBigNumber} is expected. This models the semantic of SL: It
     * only has an arbitrary precision Number type (implemented as {@link SLBigNumber}, and
     * {@code long} is only used as a performance optimization to avoid the costly
     * {@link SLBigNumber} arithmetic for values that fit into a 64-bit primitive value.
     */
    /*
    @ImplicitCast
    @TruffleBoundary
    public static SLBigNumber castBigNumber(long value) {
        return new SLBigNumber(BigInteger.valueOf(value));
    }
    */
}
