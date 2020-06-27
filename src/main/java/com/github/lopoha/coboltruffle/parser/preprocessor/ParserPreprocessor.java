package com.github.lopoha.coboltruffle.parser.preprocessor;

import com.github.lopoha.coboltruffle.parser.antlr.CobolPreprocessorLexer;
import com.github.lopoha.coboltruffle.parser.antlr.CobolPreprocessorParser;
import com.github.lopoha.coboltruffle.parser.common.ParserCommonHelper;
import com.github.lopoha.coboltruffle.parser.common.ParserSettings;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


// todo: should >CALL PROGRAM< be also preprocessed and included? -> investigate
//       maybe return the preprocessed source + a list of programs to also preprocess and let
//       the caller decide?
//       or just return a list of all the preprocessed programs?
//       including preprocessed programs have the problem that all of the call program statements
//       have to call available cobol programs -> bad
//       this also makes it hard to replace a cobol program
//       with a java/kotlin/enso/... program. -> bad
// todo: how do programs behave when they are included multiple times? do the share the memory or
//       do they get separate one? -> investigate
public class ParserPreprocessor {
  public static String getPreprocessedString(String programName,
                                                  ParserSettings settings) {
    ParserPreprocessor parserPreprocessor = new ParserPreprocessor(settings);
    return parserPreprocessor.getPreprocessedProgramSource(programName);
  }

  private final ParserSettings parserSettings;

  ParserPreprocessor(ParserSettings parserSettings) {
    this.parserSettings = parserSettings;
  }

  String getPreprocessedCopySource(String name) {
    String source = ParserCommonHelper.loadCopyFromFile(name, this.parserSettings);
    return getPreprocessedSource(source);
  }

  String getPreprocessedProgramSource(String name) {
    String source = ParserCommonHelper.loadProgramFromFile(name, this.parserSettings);
    return getPreprocessedSource(source);
  }

  private String getPreprocessedSource(String source) {
    try {
      CharStream input = CharStreams.fromString(source);
      CobolPreprocessorLexer lexer = new CobolPreprocessorLexer(input);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      CobolPreprocessorParser parser = new CobolPreprocessorParser(tokens);
      CobolPreprocessorParser.FileContext fileContext = parser.file();
      ParseTreeWalker walker = new ParseTreeWalker();
      ParserPreprocessorListener listener = new ParserPreprocessorListener(this);
      walker.walk(listener, fileContext);
      return listener.getSource();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}