package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.NotImplementedException;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser;
import com.github.lopoha.coboltruffle.parser.heap.HeapBuilderOld;
import com.github.lopoha.coboltruffle.parser.heap.HeapBuilderVariableOld;
import com.github.lopoha.coboltruffle.parser.heap.HeapVariableType;

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
  static String variableGetStringValue(
      CobolParser.VariableValueStringContext ctx, String variableName, int size) {
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
        throw new RuntimeException(
            String.format(
                "The variable \"%s\" with the given length [%d] "
                    + "was too small for \"\" (length: [%d]",
                variableName, size, removedQuotes.length()));
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
  static String variableGetNumberValue(
      CobolParser.VariableValueNumberContext ctx, String variableName, int size) {
    // todo: cleanup
    if (ctx == null) {
      return null;
    } else {
      String string = ctx.NUMBER().getText();
      if (string.length() <= size) {
        return " ".repeat(size - string.length()) + string;
      } else {
        throw new RuntimeException(
            String.format(
                "The variable \"%s\" with the given length [%d] "
                    + "was too small for \"\" (length: [%d]",
                variableName, size, string.length()));
      }
    }
  }

  // todo: cleanup + combine with addNamedVariable
  static HeapBuilderVariableOld createFillerVariable(
      String redefines, int level, CobolParser.VariableDataTypeContext dataType) {
    if (dataType == null) {
      return new HeapBuilderVariableOld(level, null, HeapVariableType.Filler, redefines);

    } else if (dataType.variableDataTypeString() != null) {
      CobolParser.VariableDataTypeStringContext dataTypeString = dataType.variableDataTypeString();
      final int size = variableGetStringLength(dataTypeString);
      final String value = variableGetStringValue(dataTypeString.variableValueString(), null, size);
      return new HeapBuilderVariableOld(level, null, HeapVariableType.Filler, size, value);

    } else if (dataType.variableDataTypeNumber() != null) {
      CobolParser.VariableDataTypeNumberContext dataTypeNumber = dataType.variableDataTypeNumber();
      // todo: allow comma values e.g. 999.99
      final int size = variableGetNumberSize(dataTypeNumber);
      final String value = variableGetNumberValue(dataTypeNumber.variableValueNumber(), null, size);
      return new HeapBuilderVariableOld(level, null, HeapVariableType.Filler, size, value);
    } else {
      throw new NotImplementedException();
    }
  }

  // todo: cleanup + combine with addFillerVariable
  static HeapBuilderVariableOld createNamedVariable(
      String variableName, int level, CobolParser.VariableDataTypeContext dataType) {
    // todo: allow array (table)
    if (dataType.variableDataTypeString() != null) {
      CobolParser.VariableDataTypeStringContext dataTypeString = dataType.variableDataTypeString();
      final int size = variableGetStringLength(dataTypeString);
      final String value =
          variableGetStringValue(dataTypeString.variableValueString(), variableName, size);
      return new HeapBuilderVariableOld(level, variableName, HeapVariableType.String, size, value);

    } else if (dataType.variableDataTypeNumber() != null) {
      CobolParser.VariableDataTypeNumberContext dataTypeNumber = dataType.variableDataTypeNumber();
      // todo: allow comma values e.g. 999.99
      final int size = variableGetNumberSize(dataTypeNumber);
      final String value =
          variableGetNumberValue(dataTypeNumber.variableValueNumber(), variableName, size);
      return new HeapBuilderVariableOld(level, variableName, HeapVariableType.Number, size, value);

    } else {
      throw new NotImplementedException();
    }
  }

  // todo: cleanup!!! + names + split
  static HeapBuilderVariableOld createVariableDefinition(
      CobolParser.VariableVariableContext variableContext) {

    final int level = Integer.parseInt(variableContext.NUMBER().getText());
    final CobolParser.VariableRedefinesContext redefinesContext =
        variableContext.variableRedefines();
    final String redefines = redefinesContext == null ? null : redefinesContext.ID().getText();

    if (variableContext.FILLER() != null) {
      final CobolParser.VariableDataTypeContext dataType = variableContext.variableDataType();
      return createFillerVariable(redefines, level, dataType);
    } else {
      final String variableName = variableContext.ID().getText();
      final CobolParser.VariableDataTypeContext dataType = variableContext.variableDataType();
      if (dataType == null) {
        // todo: handle redefines
        return new HeapBuilderVariableOld(level, variableName, HeapVariableType.None, redefines);
      } else {
        return createNamedVariable(variableName, level, dataType);
      }
    }
  }

  static void addVariable(CobolParser.VariableDefinitionContext ctx, HeapBuilderOld heapBuilder) {
    if (ctx.variableVariable() != null) {
      heapBuilder.add(createVariableDefinition(ctx.variableVariable()));
    } else if (ctx.variableConst() != null) {
      CobolParser.VariableConstContext constContext = ctx.variableConst();
      HeapBuilderVariableOld parent = heapBuilder.getLastVariable();
      // todo: allow other values
      // todo: match size with parent
      String value = constContext.variableValueString().STRING().getText();
      final HeapBuilderVariableOld variable =
          new HeapBuilderVariableOld(
              88, constContext.ID().getText(), HeapVariableType.Const, parent.getSize(), value);
      heapBuilder.add(variable);
    } else {
      throw new NotImplementedException();
    }
  }
}
