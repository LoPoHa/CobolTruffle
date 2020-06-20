package com.github.lopoha.coboltruffle.language;

import com.oracle.truffle.api.CallTarget;

// todo: don't import everything;
import com.github.lopoha.coboltruffle.parser.*;
import com.github.lopoha.coboltruffle.parser.common.*;

import java.util.List;
import java.util.ArrayList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

// todo: test performance for string, bytearrayoutputstream, ... for the preprocessor -> parser pipeline
//       the parser should call the preprocessor, it is called separatly here for demo purpose;

public class Test {
    public static void main(final String[] args) throws Exception {
        List<String> programSearchPath = new ArrayList<>();
        programSearchPath.add("./teststuff/program");
        List<String> copySearchPath = new ArrayList<>();
        copySearchPath.add("./teststuff/copy");
        ParserSettings parserSettings = new ParserSettings(copySearchPath, programSearchPath);
        Temp temp = new Temp();
        String source = temp.demo_getPreprocessedString("test", parserSettings);
        temp.demo_processPreprocessed(source);
    }
}