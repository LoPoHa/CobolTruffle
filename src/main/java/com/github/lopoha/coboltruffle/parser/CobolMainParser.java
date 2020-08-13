package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.github.lopoha.coboltruffle.heap.HeapBuilder;
import com.github.lopoha.coboltruffle.parser.antlr.CobolLexer;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.source.Source;
import java.io.File;
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
  private final Map<String, RootCallTarget> programs = new HashMap<>();

  private CobolMainParser(CobolLanguage cobolLanguage,
                          ParserSettings parserSettings) {
    assert cobolLanguage != null;
    assert parserSettings != null;

    this.cobolLanguage = cobolLanguage;
    this.parserSettings = parserSettings;
  }

  /**
   * Get the filename from the source.
   * @param source the source.
   * @return filename without extension.
   */
  public static String getFilenameWithoutExtension(Source source) {
    String name = source.getName();
    return getFilenameWithoutExtension(name);
  }

  /**
   * Get the filename from the filename.
   * @param name the name of the file with extenion.
   * @return filename without extension.
   */
  public static String getFilenameWithoutExtension(String name) {
    int pos = name.lastIndexOf(".");
    if (pos > 0) {
      name = name.substring(0, pos);
    }
    return name;
  }


  /**
   * TODO: replace method.
   *
   * @param source the preprocessed source code.
   * @return a map with all functions.
   */
  public static Map<String, RootCallTarget> processSource(Source source,
                                                          CobolLanguage cobolLanguage,
                                                          ParserSettings parserSettings) {

    CobolMainParser cobolMainParser = new CobolMainParser(cobolLanguage, parserSettings);
    cobolMainParser.parseProgram(source);
    return cobolMainParser.programs;
  }

  private void parseProgram(Source source) {
    try {
      CharStream input = CharStreams.fromString(source.getCharacters().toString());
      CobolLexer lexer = new CobolLexer(input);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      CobolParser parser = new CobolParser(tokens);
      CobolParser.ProgramContext programContext = parser.program();
      ParseTreeWalker walker = new ParseTreeWalker();


      CobolBaseListenerImpl listener = new CobolBaseListenerImpl(this, source);
      walker.walk(listener, programContext);

      this.programs.put(getFilenameWithoutExtension(source), listener.getConstructor());
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
    String key = getFilenameWithoutExtension(file.getName());

    if (this.parsedPrograms.containsKey(key)) {
      return this.parsedPrograms.get(key);
    } else {
      // todo
      Source programSource = this.getProgramSource(name);
      this.parseProgram(programSource);
      CobolProgramInfo programInfo = this.parsedPrograms.get(key);
      assert programInfo != null;
      return programInfo;
    }
  }

  void addProgram(CobolProgramInfo cobolProgramInfo) {
    this.parsedPrograms.put(cobolProgramInfo.getName(), cobolProgramInfo);
  }
}
