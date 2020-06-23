package com.github.lopoha.coboltruffle.parser;


import com.github.lopoha.coboltruffle.parser.antlr.*;
import com.github.lopoha.coboltruffle.parser.common.*;
import com.github.lopoha.coboltruffle.parser.preprocessor.*;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.nodes.RootNode;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

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


            System.out.println("\n=============   Definitions   =============\n");
            System.out.println("\n------------- WORKING STORAGE HEAP HEAP -------------\n");
            listener.workingStorageHeap.prettyPrint();
            System.out.println("\n\n------------- LINKAGE HEAP -------------\n");
            listener.linkageHeap.prettyPrint();

            System.out.println("\n=============   Actual Heap =============\n");
            CobolHeap workingStorageHeap = new CobolHeap();
            workingStorageHeap.addToHeap(listener.workingStorageHeap);

            CobolHeap linkageHeap = new CobolHeap();
            linkageHeap.addToHeap(listener.linkageHeap);

            System.out.println("\n------------- WORKING STORAGE HEAP HEAP -------------\n");
            System.out.print("[");
            System.out.print(workingStorageHeap.getHeap());
            System.out.println("]");

            HeapPointer copystring = workingStorageHeap.getHeapPointer("COPY-STRING");
            copystring.setValue("");

            HeapPointer copystringredefine1 = workingStorageHeap.getHeapPointer("COPY-STRING-REDEFINE1");

            CobolNodeFactory cobolNodeFactory = new CobolNodeFactory(cobolLanguage);
            cobolNodeFactory.startSection("main");
            CobolMoveNode moveNode = new CobolMoveNode("ABC", copystringredefine1);
            cobolNodeFactory.addMove(moveNode);
            cobolNodeFactory.finishSection();
            return cobolNodeFactory.getAllSections();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}