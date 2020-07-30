package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.github.lopoha.coboltruffle.heap.HeapBuilder;
import com.github.lopoha.coboltruffle.parser.antlr.CobolLexer;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.source.Source;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

// todo: better name
public class CobolMainParser {
  private final CobolLanguage cobolLanguage;
  private final ParserSettings parserSettings;
  private final Map<String, CobolProgramInfo> parsedPrograms = new HashMap<>();
  private final Map<String, HeapBuilder> heapBuilderCache = new HashMap<>();

  private CobolMainParser(CobolLanguage cobolLanguage,
                          ParserSettings parserSettings) {
    assert cobolLanguage != null;
    assert parserSettings != null;

    this.cobolLanguage = cobolLanguage;
    this.parserSettings = parserSettings;
  }

  /**
   * TODO: replace method.
   *
   * @param source the preprocessed source code.
   * @return a map with all functions.
   */
  public static RootCallTarget processSource(Source source,
                                              CobolLanguage cobolLanguage,
                                              ParserSettings parserSettings) {

    CobolMainParser cobolMainParser = new CobolMainParser(cobolLanguage, parserSettings);
    return cobolMainParser.parseProgram(source);
  }

  private RootCallTarget parseProgram(Source source) {
    try {
      CharStream input = CharStreams.fromString(source.getCharacters().toString());
      CobolLexer lexer = new CobolLexer(input);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      CobolParser parser = new CobolParser(tokens);
      CobolParser.ProgramContext programContext = parser.program();
      ParseTreeWalker walker = new ParseTreeWalker();


      CobolBaseListenerImpl listener = new CobolBaseListenerImpl(this, source);
      walker.walk(listener, programContext);

      return listener.getConstructor();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  Source getCopySource(String name) {
    File file = ParserCommonHelper.getCopyFile(name, this.parserSettings);
    try {
      return Source.newBuilder(CobolLanguage.ID, file.toURI().toURL()).build();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  HeapBuilder processStorageCopy(Source source) {
    if (this.heapBuilderCache.containsKey(source.getPath())) {
      return this.heapBuilderCache.get(source.getPath());
    } else {
      try {
        CharStream input = CharStreams.fromString(source.getCharacters().toString());
        CobolLexer lexer = new CobolLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CobolParser parser = new CobolParser(tokens);
        CobolParser.VariableDefinitionCopyContext copyContext = parser.variableDefinitionCopy();
        ParseTreeWalker walker = new ParseTreeWalker();
        CobolStorageCopyListenerImpl listener
            = new CobolStorageCopyListenerImpl(this, source);
        walker.walk(listener, copyContext);

        HeapBuilder heapBuilder = listener.getHeap();
        this.heapBuilderCache.put(source.getPath(), heapBuilder);
        return heapBuilder;
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  Source getProgramSource(String name) {
    File file = ParserCommonHelper.getProgramFile(name, this.parserSettings);
    try {
      return Source.newBuilder(CobolLanguage.ID, file.toURI().toURL()).build();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  CobolLanguage getCobolLanguage() {
    return cobolLanguage;
  }

  CobolProgramInfo getProgram(String name) {
    File file = ParserCommonHelper.getProgramFile(name, this.parserSettings);
    if (this.parsedPrograms.containsKey(file.getPath())) {
      return this.parsedPrograms.get(file.getPath());
    } else {
      // todo
      Source programSource = this.getProgramSource(name);
      RootCallTarget constructor = this.parseProgram(programSource);
      CobolProgramInfo programInfo
          = new CobolProgramInfo(file.getPath(), programSource, new ArrayList<>(), constructor);
      this.parsedPrograms.put(file.getPath(), programInfo);
      return programInfo;
    }
  }

  void addProgram(CobolProgramInfo cobolProgramInfo) {
    this.parsedPrograms.put(cobolProgramInfo.getPath(), cobolProgramInfo);
  }
}
