// CheckStyle: start generated

package com.github.lopoha.coboltruffle.parser;


import com.github.lopoha.coboltruffle.parser.CobolLanguage;

import com.oracle.truffle.api.TruffleLanguage;

import com.oracle.truffle.api.TruffleFile.FileTypeDetector;

import com.oracle.truffle.api.TruffleLanguage.ContextPolicy;

import com.oracle.truffle.api.TruffleLanguage.Provider;

import com.oracle.truffle.api.TruffleLanguage.Registration;

import com.oracle.truffle.api.debug.DebuggerTags.AlwaysHalt;

import com.oracle.truffle.api.dsl.GeneratedBy;

import com.oracle.truffle.api.instrumentation.ProvidedTags;

import com.oracle.truffle.api.instrumentation.StandardTags.CallTag;

import com.oracle.truffle.api.instrumentation.StandardTags.ExpressionTag;

import com.oracle.truffle.api.instrumentation.StandardTags.ReadVariableTag;

import com.oracle.truffle.api.instrumentation.StandardTags.RootBodyTag;

import com.oracle.truffle.api.instrumentation.StandardTags.RootTag;

import com.oracle.truffle.api.instrumentation.StandardTags.StatementTag;

import com.oracle.truffle.api.instrumentation.StandardTags.WriteVariableTag;

import java.util.Collection;

import java.util.Collections;

import java.util.List;


@GeneratedBy(CobolLanguage.class)

@Registration(characterMimeTypes = {"application/x-cbl"}, contextPolicy = ContextPolicy.SHARED, defaultMimeType = "application/x-cbl", id = "Cobol", name = "Cobol")

@ProvidedTags({CallTag.class, StatementTag.class, RootTag.class, RootBodyTag.class, ExpressionTag.class, AlwaysHalt.class, ReadVariableTag.class, WriteVariableTag.class})

public final class CobolLanguageProvider implements Provider {


    @Override

    public String getLanguageClassName() {

        return "com.github.lopoha.coboltruffle.parser.CobolLanguage";

    }


    @Override

    public TruffleLanguage<?> create() {

        return new CobolLanguage();

    }


    @Override

    public List<FileTypeDetector> createFileTypeDetectors() {

        return Collections.emptyList();

    }


    @Override

    public Collection<String> getServicesClassNames() {

        return Collections.emptySet();

    }


}
