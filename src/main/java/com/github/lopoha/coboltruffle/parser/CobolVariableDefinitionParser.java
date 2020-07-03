package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.parser.antlr.CobolParser;

class CobolVariableDefinitionParser {
  static int variableGetStringLength(CobolParser.VariableDataTypeStringContext ctx) {
    if (ctx.PICXS() != null) {
      return ctx.PICXS().getText().length();
    } else {
      CobolParser.VariableSizeContext sizeContext = ctx.variableSize();
      return (sizeContext == null || sizeContext.NUMBER() == null)
          ? 1
          : Integer.parseInt(sizeContext.NUMBER().getText());
    }
  }


  // either returns null if no value was specified or a string with the length of the given size
  // and the value right bound + filled with space if it is shorter.
  static String variableGetStringValue(CobolParser.VariableValueStringContext ctx,
                                       String variableName, int size) {
    // todo: cleanup
    if (ctx == null) {
      return null;
    } else if (ctx.SPACE() != null) {
      return " ".repeat(size);
    } else {
      String string = ctx.STRING().getText();
      String removedQuotes = string.substring(1, string.length() - 1);
      if (removedQuotes.length() <= size) {
        return " ".repeat(size - removedQuotes.length()) + removedQuotes;
      } else {
        throw new RuntimeException(String.format("The variable \"%s\" with the given length [%d] "
                + "was too small for \"\" (length: [%d]",
            variableName,
            size,
            removedQuotes.length()));
      }
    }
  }

  static int variableGetNumberSize(CobolParser.VariableDataTypeNumberContext ctx) {
    if (ctx.PIC9S() != null) {
      return ctx.PIC9S().getText().length();
    } else {
      CobolParser.VariableSizeContext sizeContext = ctx.variableSize();
      return (sizeContext == null || sizeContext.NUMBER() == null)
          ? 1
          : Integer.parseInt(sizeContext.NUMBER().getText());
    }
  }

  // either returns null if no value was specified or a string with the length of the given size
  // and the value right bound + filled with space if it is shorter.
  static String variableGetNumberValue(CobolParser.VariableValueNumberContext ctx,
                                       String variableName,
                                       int size) {
    // todo: cleanup
    if (ctx == null) {
      return null;
    } else {
      String string = ctx.NUMBER().getText();
      if (string.length() <= size) {
        return " ".repeat(size - string.length()) + string;
      } else {
        throw new RuntimeException(String.format("The variable \"%s\" with the given length [%d] "
            + "was too small for \"\" (length: [%d]", variableName, size, string.length()));
      }
    }
  }
}
