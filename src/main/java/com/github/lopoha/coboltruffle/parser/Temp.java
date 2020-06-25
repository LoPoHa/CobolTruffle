package com.github.lopoha.coboltruffle.parser;


import com.github.lopoha.coboltruffle.parser.antlr.*;
import com.github.lopoha.coboltruffle.parser.common.*;
import com.github.lopoha.coboltruffle.parser.nodes.CobolMoveNode;
import com.github.lopoha.coboltruffle.parser.preprocessor.*;

import com.oracle.truffle.api.RootCallTarget;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.IOException;
import java.util.Map;

public class Temp {
    public String demo_getPreprocessedString(String programName, ParserSettings settings) throws IOException {
        ParserPreprocessor parserPreprocessor = new ParserPreprocessor(settings);
        String preprocessedSource = parserPreprocessor.getPreprocessedProgramSource(programName);
        return preprocessedSource;
    }

    public Map<String, RootCallTarget> demo_processPreprocessed(String source, CobolLanguage cobolLanguage) {
        try {
            CharStream input = CharStreams.fromString(source);
            CobolLexer lexer = new CobolLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CobolParser parser = new CobolParser(tokens);
            CobolParser.FileContext fileContext = parser.file();
            ParseTreeWalker walker = new ParseTreeWalker();
            CobolBaseListenerImpl listener = new CobolBaseListenerImpl();
            walker.walk(listener, fileContext);

            CobolHeap workingStorageHeap = new CobolHeap();
            workingStorageHeap.addToHeap(listener.workingStorageHeap);

            HeapPointer copystring = workingStorageHeap.getHeapPointer("COPY-STRING");

            HeapPointer programName = workingStorageHeap.getHeapPointer("PROGRAMNAME");

            CobolNodeFactory cobolNodeFactory = new CobolNodeFactory(cobolLanguage);
            cobolNodeFactory.startSection("main");
            CobolMoveNode moveNode = new CobolMoveNode("ABC", programName);
            cobolNodeFactory.addMove(moveNode);
            CobolMoveNode moveNode2 = new CobolMoveNode(copystring, programName);
            cobolNodeFactory.addMove(moveNode2);
            cobolNodeFactory.finishSection();
            return cobolNodeFactory.getAllSections();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}