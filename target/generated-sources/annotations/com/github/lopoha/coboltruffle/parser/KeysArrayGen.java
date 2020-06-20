// CheckStyle: start generated

package com.github.lopoha.coboltruffle.parser;


import com.github.lopoha.coboltruffle.parser.CobolLexicalScope.KeysArray;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;

import com.oracle.truffle.api.dsl.GeneratedBy;

import com.oracle.truffle.api.interop.InteropLibrary;

import com.oracle.truffle.api.interop.InvalidArrayIndexException;

import com.oracle.truffle.api.interop.UnsupportedMessageException;

import com.oracle.truffle.api.library.DynamicDispatchLibrary;

import com.oracle.truffle.api.library.LibraryExport;

import com.oracle.truffle.api.library.LibraryFactory;

import com.oracle.truffle.api.nodes.NodeCost;


@GeneratedBy(KeysArray.class)

final class KeysArrayGen {


    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);


    static  {

        LibraryExport.register(KeysArray.class, new InteropLibraryExports());

    }


    private KeysArrayGen() {

    }


    @GeneratedBy(KeysArray.class)

    private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {


        private static final Uncached UNCACHED = new Uncached();

        private static final Cached CACHE = new Cached();


        private InteropLibraryExports() {

            super(InteropLibrary.class, KeysArray.class, false);

        }


        @Override

        protected InteropLibrary createUncached(Object receiver) {

            assert receiver instanceof KeysArray;

            InteropLibrary uncached = InteropLibraryExports.UNCACHED;

            return uncached;

        }


        @Override

        protected InteropLibrary createCached(Object receiver) {

            assert receiver instanceof KeysArray;

            return InteropLibraryExports.CACHE;

        }


        @GeneratedBy(KeysArray.class)

        private static final class Cached extends InteropLibrary {


            Cached() {

            }


            @Override

            public boolean accepts(Object receiver) {

                assert !(receiver instanceof KeysArray) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export 'com.github.lopoha.coboltruffle.parser.CobolLexicalScope.KeysArray'. Exported receiver with dynamic dispatch found but not expected.";

                return receiver instanceof KeysArray;

            }


            @Override

            public boolean isAdoptable() {

                return false;

            }


            @Override

            public boolean hasArrayElements(Object receiver) {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return (((KeysArray) receiver)).hasArrayElements();

            }


            @Override

            public boolean isArrayElementReadable(Object receiver, long index) {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return (((KeysArray) receiver)).isArrayElementReadable(index);

            }


            @Override

            public long getArraySize(Object receiver) throws UnsupportedMessageException {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return (((KeysArray) receiver)).getArraySize();

            }


            @Override

            public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return (((KeysArray) receiver)).readArrayElement(index);

            }


        }

        @GeneratedBy(KeysArray.class)

        private static final class Uncached extends InteropLibrary {


            Uncached() {

            }


            @TruffleBoundary

            @Override

            public boolean accepts(Object receiver) {

                assert !(receiver instanceof KeysArray) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export 'com.github.lopoha.coboltruffle.parser.CobolLexicalScope.KeysArray'. Exported receiver with dynamic dispatch found but not expected.";

                return receiver instanceof KeysArray;

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

            public boolean hasArrayElements(Object receiver) {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return ((KeysArray) receiver) .hasArrayElements();

            }


            @TruffleBoundary

            @Override

            public boolean isArrayElementReadable(Object receiver, long index) {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return ((KeysArray) receiver) .isArrayElementReadable(index);

            }


            @TruffleBoundary

            @Override

            public long getArraySize(Object receiver) throws UnsupportedMessageException {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return ((KeysArray) receiver) .getArraySize();

            }


            @TruffleBoundary

            @Override

            public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return ((KeysArray) receiver) .readArrayElement(index);

            }


        }

    }

}
