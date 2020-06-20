// CheckStyle: start generated

package com.github.lopoha.coboltruffle.parser;


import com.github.lopoha.coboltruffle.parser.CobolNull;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;

import com.oracle.truffle.api.dsl.GeneratedBy;

import com.oracle.truffle.api.interop.InteropLibrary;

import com.oracle.truffle.api.library.DynamicDispatchLibrary;

import com.oracle.truffle.api.library.LibraryExport;

import com.oracle.truffle.api.library.LibraryFactory;

import com.oracle.truffle.api.nodes.NodeCost;


@GeneratedBy(CobolNull.class)

final class CobolNullGen {


    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);


    static  {

        LibraryExport.register(CobolNull.class, new InteropLibraryExports());

    }


    private CobolNullGen() {

    }


    @GeneratedBy(CobolNull.class)

    private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {


        private static final Uncached UNCACHED = new Uncached();

        private static final Cached CACHE = new Cached();


        private InteropLibraryExports() {

            super(InteropLibrary.class, CobolNull.class, false);

        }


        @Override

        protected InteropLibrary createUncached(Object receiver) {

            assert receiver instanceof CobolNull;

            InteropLibrary uncached = InteropLibraryExports.UNCACHED;

            return uncached;

        }


        @Override

        protected InteropLibrary createCached(Object receiver) {

            assert receiver instanceof CobolNull;

            return InteropLibraryExports.CACHE;

        }


        @GeneratedBy(CobolNull.class)

        private static final class Cached extends InteropLibrary {


            Cached() {

            }


            @Override

            public boolean accepts(Object receiver) {

                assert !(receiver instanceof CobolNull) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export 'com.github.lopoha.coboltruffle.parser.CobolNull'. Exported receiver with dynamic dispatch found but not expected.";

                return receiver instanceof CobolNull;

            }


            @Override

            public boolean isAdoptable() {

                return false;

            }


            @Override

            public boolean isNull(Object receiver) {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return (((CobolNull) receiver)).isNull();

            }


        }

        @GeneratedBy(CobolNull.class)

        private static final class Uncached extends InteropLibrary {


            Uncached() {

            }


            @TruffleBoundary

            @Override

            public boolean accepts(Object receiver) {

                assert !(receiver instanceof CobolNull) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export 'com.github.lopoha.coboltruffle.parser.CobolNull'. Exported receiver with dynamic dispatch found but not expected.";

                return receiver instanceof CobolNull;

            }


            @Override

            public boolean isAdoptable() {

                return false;

            }


            @Override

            public NodeCost getCost() {

                return NodeCost.MEGAMORPHIC;

            }


            @TruffleBoundary

            @Override

            public boolean isNull(Object receiver) {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return ((CobolNull) receiver) .isNull();

            }


        }

    }

}
