// CheckStyle: start generated

package com.github.lopoha.coboltruffle.parser;


import com.github.lopoha.coboltruffle.parser.CobolTypes;

import com.oracle.truffle.api.dsl.GeneratedBy;

import com.oracle.truffle.api.nodes.UnexpectedResultException;


@GeneratedBy(CobolTypes.class)

public final class CobolTypesGen extends CobolTypes {


    @Deprecated public static final CobolTypesGen COBOL_TYPES = new CobolTypesGen();


    protected CobolTypesGen() {

    }


    public static boolean isLong(Object value) {

        return value instanceof Long;

    }


    public static long asLong(Object value) {

        assert value instanceof Long : "CobolTypesGen.asLong: long expected";

        return (long) value;

    }


    public static long expectLong(Object value) throws UnexpectedResultException {

        if (value instanceof Long) {

            return (long) value;

        }

        throw new UnexpectedResultException(value);

    }


    public static boolean isBoolean(Object value) {

        return value instanceof Boolean;

    }


    public static boolean asBoolean(Object value) {

        assert value instanceof Boolean : "CobolTypesGen.asBoolean: boolean expected";

        return (boolean) value;

    }


    public static boolean expectBoolean(Object value) throws UnexpectedResultException {

        if (value instanceof Boolean) {

            return (boolean) value;

        }

        throw new UnexpectedResultException(value);

    }


}
