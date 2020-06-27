package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.parser.antlr.CobolLexer;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser;
import com.github.lopoha.coboltruffle.parser.common.ParserSettings;
import com.github.lopoha.coboltruffle.parser.expression.CobolFunctionLiteralNode;
import com.github.lopoha.coboltruffle.parser.expression.CobolStringLiteralNode;
import com.github.lopoha.coboltruffle.parser.nodes.CobolMoveNode;
import com.github.lopoha.coboltruffle.parser.preprocessor.ParserPreprocessor;
import com.oracle.truffle.api.RootCallTarget;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * TODO remove this class.
 */
public class Temp {
  public String demo_getPreprocessedString(String programName,
                                           ParserSettings settings) {
    ParserPreprocessor parserPreprocessor = new ParserPreprocessor(settings);
    return parserPreprocessor.getPreprocessedProgramSource(programName);
  }

  /**
   * TODO: replace method.
   * @param source the preprocessed source code.
   * @param cobolLanguage a reference to the cobollanguage.
   * @return a map with all functions.
   */
  public Map<String, RootCallTarget> demo_processPreprocessed(String source,
                                                              CobolLanguage cobolLanguage) {
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



      CobolNodeFactory cobolNodeFactory = new CobolNodeFactory(cobolLanguage);
      cobolNodeFactory.startSection("main");

      CobolStringLiteralNode stringConstant = new CobolStringLiteralNode("hello World");
      CobolFunctionLiteralNode println = new CobolFunctionLiteralNode("display");
      List<CobolExpressionNode> printlnArgs = new ArrayList<>();
      printlnArgs.add(stringConstant);
      cobolNodeFactory.addCall(println, printlnArgs);

      HeapPointer programName = workingStorageHeap.getHeapPointer("PROGRAMNAME");
      List<CobolExpressionNode> println2Args = new ArrayList<>();
      println2Args.add(programName);
      cobolNodeFactory.addCall(println, println2Args);

      CobolMoveNode moveNode = new CobolMoveNode("ABC", programName);
      cobolNodeFactory.addMove(moveNode);
      cobolNodeFactory.addCall(println, println2Args);

      HeapPointer copyString = workingStorageHeap.getHeapPointer("COPY-STRING");
      CobolMoveNode moveNode2 = new CobolMoveNode(copyString, programName);
      cobolNodeFactory.addMove(moveNode2);
      cobolNodeFactory.addCall(println, println2Args);

      cobolNodeFactory.finishSection();
      return cobolNodeFactory.getAllSections();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}