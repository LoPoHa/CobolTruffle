// CheckStyle: start generated

package com.github.lopoha.coboltruffle.parser;


import com.github.lopoha.coboltruffle.parser.CobolLexicalScope.VariablesMapObject;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;

import com.oracle.truffle.api.dsl.GeneratedBy;

import com.oracle.truffle.api.interop.InteropLibrary;

import com.oracle.truffle.api.interop.UnknownIdentifierException;

import com.oracle.truffle.api.interop.UnsupportedMessageException;

import com.oracle.truffle.api.interop.UnsupportedTypeException;

import com.oracle.truffle.api.library.DynamicDispatchLibrary;

import com.oracle.truffle.api.library.LibraryExport;

import com.oracle.truffle.api.library.LibraryFactory;

import com.oracle.truffle.api.nodes.NodeCost;


@GeneratedBy(VariablesMapObject.class)

final class VariablesMapObjectGen {


    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);


    static  {

        LibraryExport.register(VariablesMapObject.class, new InteropLibraryExports());

    }


    private VariablesMapObjectGen() {

    }


    @GeneratedBy(VariablesMapObject.class)

    private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {


        private static final Uncached UNCACHED = new Uncached();

        private static final Cached CACHE = new Cached();


        private InteropLibraryExports() {

            super(InteropLibrary.class, VariablesMapObject.class, false);

        }


        @Override

        protected InteropLibrary createUncached(Object receiver) {

            assert receiver instanceof VariablesMapObject;

            InteropLibrary uncached = InteropLibraryExports.UNCACHED;

            return uncached;

        }


        @Override

        protected InteropLibrary createCached(Object receiver) {

            assert receiver instanceof VariablesMapObject;

            return InteropLibraryExports.CACHE;

        }


        @GeneratedBy(VariablesMapObject.class)

        private static final class Cached extends InteropLibrary {


            Cached() {

            }


            @Override

            public boolean accepts(Object receiver) {

                assert !(receiver instanceof VariablesMapObject) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export 'com.github.lopoha.coboltruffle.parser.CobolLexicalScope.VariablesMapObject'. Exported receiver with dynamic dispatch found but not expected.";

                return receiver instanceof VariablesMapObject;

            }


            @Override

            public boolean isAdoptable() {

                return false;

            }


            @Override

            public boolean hasMembers(Object receiver) {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return (((VariablesMapObject) receiver)).hasMembers();

            }


            @Override

            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return (((VariablesMapObject) receiver)).getMembers(includeInternal);

            }


            @Override

            public void writeMember(Object receiver, String member, Object value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                (((VariablesMapObject) receiver)).writeMember(member, value);

                return;

            }


            @Override

            public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return (((VariablesMapObject) receiver)).readMember(member);

            }


            @Override

            public boolean isMemberInsertable(Object receiver, String member) {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return (((VariablesMapObject) receiver)).isMemberInsertable(member);

            }


            @Override

            public boolean isMemberModifiable(Object receiver, String member) {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return (((VariablesMapObject) receiver)).isMemberModifiable(member);

            }


            @Override

            public boolean isMemberReadable(Object receiver, String member) {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return (((VariablesMapObject) receiver)).isMemberReadable(member);

            }


        }

        @GeneratedBy(VariablesMapObject.class)

        private static final class Uncached extends InteropLibrary {


            Uncached() {

            }


            @TruffleBoundary

            @Override

            public boolean accepts(Object receiver) {

                assert !(receiver instanceof VariablesMapObject) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export 'com.github.lopoha.coboltruffle.parser.CobolLexicalScope.VariablesMapObject'. Exported receiver with dynamic dispatch found but not expected.";

                return receiver instanceof VariablesMapObject;

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

            public boolean hasMembers(Object receiver) {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return ((VariablesMapObject) receiver) .hasMembers();

            }


            @TruffleBoundary

            @Override

            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return ((VariablesMapObject) receiver) .getMembers(includeInternal);

            }


            @TruffleBoundary

            @Override

            public void writeMember(Object receiver, String member, Object value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                ((VariablesMapObject) receiver) .writeMember(member, value);

                return;

            }


            @TruffleBoundary

            @Override

            public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return ((VariablesMapObject) receiver) .readMember(member);

            }


            @TruffleBoundary

            @Override

            public boolean isMemberInsertable(Object receiver, String member) {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return ((VariablesMapObject) receiver) .isMemberInsertable(member);

            }


            @TruffleBoundary

            @Override

            public boolean isMemberModifiable(Object receiver, String member) {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return ((VariablesMapObject) receiver) .isMemberModifiable(member);

            }


            @TruffleBoundary

            @Override

            public boolean isMemberReadable(Object receiver, String member) {

                assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

                return ((VariablesMapObject) receiver) .isMemberReadable(member);

            }


        }

    }

}
