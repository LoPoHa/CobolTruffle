package com.github.lopoha.coboltruffle.parser.preprocessor;

import com.github.lopoha.coboltruffle.parser.antlr.CobolPreprocessorBaseListener;
import com.github.lopoha.coboltruffle.parser.antlr.CobolPreprocessorParser;

public class ParserPreprocessorListener extends CobolPreprocessorBaseListener {
  private final StringBuilder source = new StringBuilder();
  private final ParserPreprocessor parserPreprocessor;

  ParserPreprocessorListener(ParserPreprocessor parserPreprocessor) {
    this.parserPreprocessor = parserPreprocessor;
  }

  @Override
  public void enterCopy(CobolPreprocessorParser.CopyContext ctx) {
    this.source.append(this.parserPreprocessor.getPreprocessedCopySource(ctx.ID().getText()))
               .append(' ');
  }

  @Override
  public void enterIgnored(CobolPreprocessorParser.IgnoredContext ctx) {
    source.append(ctx.getText()).append(' ');
  }

  public String getSource() {
    return source.toString();
  }
}