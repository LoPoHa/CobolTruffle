package com.github.lopoha.coboltruffle.parser.preprocessor;

import com.github.lopoha.coboltruffle.parser.antlr.*;
import com.github.lopoha.coboltruffle.parser.common.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

//import com.github.lopoha.coboltruffle.parser.preprocessor.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// todo: should >CALL PROGRAM< be also preprocessed and included? -> investigate
//       maybe return the preprocessed source + a list of programs to also preprocess and let the caller decide?
//       or just return a list of all the preprocessed programs?
//       including preprocessed programs have the problem that all of the call program statements have to call available cobol programs -> bad
//       this also makes it hard to replace a cobol program with a java/kotlin/enso/... program. -> bad
// todo: how do programs behave when they are included multiple times? do the share the memory or do they get separate one? -> investigate
public class ParserPreprocessor {
    private final ParserSettings parserSettings;
    public ParserPreprocessor(ParserSettings parserSettings) {
        this.parserSettings = parserSettings;
    }

    public String getPreprocessedCopySource(String name) {
        String source = ParserCommonHelper.loadCopyFromFile(name, this.parserSettings);
        return getPreprocessedSource(source);
    }

    public String getPreprocessedProgramSource(String name) {
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