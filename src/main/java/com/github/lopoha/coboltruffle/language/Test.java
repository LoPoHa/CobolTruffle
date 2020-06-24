package com.github.lopoha.coboltruffle.language;

import com.oracle.truffle.api.CallTarget;

// todo: don't import everything;
import com.github.lopoha.coboltruffle.parser.*;
import com.github.lopoha.coboltruffle.parser.common.*;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.*;
import com.oracle.truffle.api.source.Source;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

// todo: test performance for string, bytearrayoutputstream, ... for the preprocessor -> parser pipeline
//       the parser should call the preprocessor, it is called separatly here for demo purpose;

public class Test {
    private static VirtualFrame createTopFrame(FrameDescriptor frameDescriptor) {
        VirtualFrame virtualFrame = Truffle.getRuntime().createVirtualFrame(
                new Object[] {}, frameDescriptor);
        return virtualFrame;
    }

    public static void main(final String[] args) throws Exception {
        VirtualFrame topFrame = createTopFrame(new FrameDescriptor());


        Source source = Source.newBuilder(CobolLanguage.ID, new FileReader((new File("./teststuff/program/test.cbl"))), "test").build();
        CobolLanguage cobolLanguage = new CobolLanguage();
        CobolContext context = new CobolContext();

        List<String> programSearchPath = new ArrayList<>();
        programSearchPath.add("./teststuff/program");
        List<String> copySearchPath = new ArrayList<>();
        copySearchPath.add("./teststuff/copy");
        ParserSettings parserSettings = new ParserSettings(copySearchPath, programSearchPath);
        String preprocessed = new Temp().demo_getPreprocessedString("test", parserSettings);

        Map<String, RootCallTarget> functions = new Temp().demo_processPreprocessed(preprocessed, null);
        RootCallTarget main = functions.get("main");

        main.call();
    }
}