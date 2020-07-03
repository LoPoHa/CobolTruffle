package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.CobolLanguage;
import com.github.lopoha.coboltruffle.heap.HeapBuilder;
import com.github.lopoha.coboltruffle.parser.antlr.CobolLexer;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.source.Source;
import java.util.Map;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

// todo: better name
public class CobolMainParser {
  /**
   * TODO: replace method.
   *
   * @param source the preprocessed source code.
   * @return a map with all functions.
   */
  public static Map<String, RootCallTarget> processSource(Source source,
                                                          CobolLanguage cobolLanguage) {
    try {
      CharStream input = CharStreams.fromString(source.getCharacters().toString());
      CobolLexer lexer = new CobolLexer(input);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      CobolParser parser = new CobolParser(tokens);
      CobolParser.ProgramContext programContext = parser.program();
      ParseTreeWalker walker = new ParseTreeWalker();
      CobolBaseListenerImpl listener = new CobolBaseListenerImpl(cobolLanguage, source);
      walker.walk(listener, programContext);

      return listener.getAllSections();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  static HeapBuilder processStorageCopy(Source source, CobolLanguage cobolLanguage) {
    // todo
    return null;
  }

}
