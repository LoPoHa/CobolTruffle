package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.NotImplementedException;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser.VariableConstContext;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser.VariableRedefinesContext;
import com.github.lopoha.coboltruffle.parser.heap.HeapBuilder;
import com.github.lopoha.coboltruffle.parser.heap.HeapBuilderVariable;
import com.github.lopoha.coboltruffle.parser.heap.HeapVariableType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class CobolVariableDefinitionParser {

  private static int variableGetStringLength(CobolParser.VariableDataTypeStringContext ctx) {
    if (ctx.PICXS() != null) {
      return ctx.PICXS().getText().length();
    } else {
      CobolParser.VariableSizeContext sizeContext = ctx.variableSize();
      return (sizeContext == null || sizeContext.NUMBER() == null)
          ? 1
          : Integer.parseInt(sizeContext.NUMBER().getText());
    }
  }

  private static int variableGetNumberSize(CobolParser.VariableDataTypeNumberContext ctx) {
    if (ctx.PIC9S() != null) {
      return ctx.PIC9S().getText().length();
    } else {
      CobolParser.VariableSizeContext sizeContext = ctx.variableSize();
      return (sizeContext == null || sizeContext.NUMBER() == null)
          ? 1
          : Integer.parseInt(sizeContext.NUMBER().getText());
    }
  }

  private static char[] variableGetValue(String variableName, String value, int size) {
    if (value.length() <= size) {
      char[] result = new char[size];
      for (int i = 0; i < size - value.length(); i++) {
        result[i] = ' ';
      }
      for (int i = size - value.length(); i < size; i++) {
        result[i] = value.charAt(i + (size - value.length()));
      }
      return result;
    } else {
      throw new CobolVariableDefaultValueTooBig(variableName, size, value);
    }
  }

  // either returns null if no value was specified or a string with the length of the given size
  // and the value right bound + filled with space if it is shorter.
  private static char[] variableGetStringValue(
      CobolParser.VariableValueStringContext ctx, String variableName, int size) {
    if (ctx == null) {
      return null;
    } else if (ctx.SPACE() != null) {
      char[] value = new char[size];
      for (char ch : value) {
        ch = ' ';
      }
      return value;
    } else {
      String string = ctx.STRING().getText();
      String removedQuotes = string.substring(1, string.length() - 1);
      return variableGetValue(variableName, removedQuotes, size);
    }
  }

  // either returns null if no value was specified or a string with the length of the given size
  // and the value right bound + filled with space if it is shorter.
  private static char[] variableGetNumberValue(
      CobolParser.VariableValueNumberContext ctx, String variableName, int size) {
    if (ctx == null) {
      return null;
    } else {
      String number = ctx.NUMBER().getText();
      return variableGetValue(variableName, number, size);
    }
  }

  // todo: cleanup + combine with addNamedVariable
  private static HeapBuilderVariable createFillerVariable(
      HeapBuilderVariable redefines, int level, CobolParser.VariableDataTypeContext dataType) {
    if (dataType == null) {
      return new HeapBuilderVariable(level, HeapVariableType.Filler, redefines);
    } else if (dataType.variableDataTypeString() != null) {
      CobolParser.VariableDataTypeStringContext dataTypeString = dataType.variableDataTypeString();
      final int size = variableGetStringLength(dataTypeString);
      final char[] value
          = variableGetStringValue(dataTypeString.variableValueString(), null, size);
      return new HeapBuilderVariable(level, HeapVariableType.Filler, size, value);

    } else if (dataType.variableDataTypeNumber() != null) {
      CobolParser.VariableDataTypeNumberContext dataTypeNumber = dataType.variableDataTypeNumber();
      // todo: allow comma values e.g. 999.99
      final int size = variableGetNumberSize(dataTypeNumber);
      final char[] value
          = variableGetNumberValue(dataTypeNumber.variableValueNumber(), null, size);
      return new HeapBuilderVariable(level, HeapVariableType.Filler, size, value);
    } else {
      throw new NotImplementedException();
    }
  }

  // todo: cleanup + combine with addFillerVariable
  private static HeapBuilderVariable createNamedVariable(
      String variableName, int level, CobolParser.VariableDataTypeContext dataType) {
    // todo: allow array (table)
    if (dataType.variableDataTypeString() != null) {
      CobolParser.VariableDataTypeStringContext dataTypeString = dataType.variableDataTypeString();
      final int size = variableGetStringLength(dataTypeString);
      final char[] value =
          variableGetStringValue(dataTypeString.variableValueString(), variableName, size);
      return new HeapBuilderVariable(variableName, level, HeapVariableType.String, size, value);

    } else if (dataType.variableDataTypeNumber() != null) {
      CobolParser.VariableDataTypeNumberContext dataTypeNumber = dataType.variableDataTypeNumber();
      // todo: allow comma values e.g. 999.99
      final int size = variableGetNumberSize(dataTypeNumber);
      final char[] value =
          variableGetNumberValue(dataTypeNumber.variableValueNumber(), variableName, size);
      return new HeapBuilderVariable(variableName, level, HeapVariableType.Number, size, value);

    } else {
      throw new NotImplementedException();
    }
  }

  private static HeapBuilderVariable getRedefinition(VariableRedefinesContext redefinesContext,
      HeapBuilder heapBuilder) {
    final String redefines = redefinesContext == null ? null : redefinesContext.ID().getText();
    return redefines == null ? null : heapBuilder.findVariable(redefines);
  }

  private static HeapBuilderVariable createVariableDefinition(
      CobolParser.VariableNonConstContext variableContext,
      HeapBuilder heapBuilder) {
    final int level = Integer.parseInt(variableContext.NUMBER().getText());
    HeapBuilderVariable redefineVariable
        = getRedefinition(variableContext.variableRedefines(), heapBuilder);

    if (variableContext.FILLER() != null) {
      final CobolParser.VariableDataTypeContext dataType = variableContext.variableDataType();
      return createFillerVariable(redefineVariable, level, dataType);
    } else {
      final String variableName = variableContext.ID().getText();
      final CobolParser.VariableDataTypeContext dataType = variableContext.variableDataType();
      if (dataType == null) {
        return new HeapBuilderVariable(variableName,
            level,
            HeapVariableType.None,
            redefineVariable);
      } else {
        return createNamedVariable(variableName, level, dataType);
      }
    }
  }

  private static HeapBuilderVariable createConstDefinition(VariableConstContext constContext,
      HeapBuilder heapBuilder) {
    HeapBuilderVariable parent = heapBuilder.getLastVariable();
    String value = constContext.variableValueString().STRING().getText();

    char[] defaultValue = new char[value.length() - 2];
    if (value.startsWith("\"")) {
      value = value.substring(1, value.length() - 1);
      for (int i = 0; i < value.length(); i++) {
        defaultValue[i] = value.charAt(i);
      }
    } else {
      throw new NotImplementedException();
    }

    return new HeapBuilderVariable(
        constContext.ID().getText(),
        88,
        HeapVariableType.Const,
        parent.getSize(),
        defaultValue);

  }

  static void addVariable(CobolParser.VariableDefinitionContext ctx, HeapBuilder heapBuilder) {
    if (ctx.variableNonConst() != null) {
      heapBuilder.add(createVariableDefinition(ctx.variableNonConst(), heapBuilder));
    } else if (ctx.variableConst() != null) {
      heapBuilder.add(createConstDefinition(ctx.variableConst(), heapBuilder));
    } else {
      throw new NotImplementedException();
    }
  }
}
