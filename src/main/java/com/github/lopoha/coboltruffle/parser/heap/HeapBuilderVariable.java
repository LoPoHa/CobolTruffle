package com.github.lopoha.coboltruffle.parser.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A Variable on the Cobol Heap. It contains all the children which level was lower than the current
 * one. <br>
 * <br>
 *
 * <ul>
 *   <li>The level must be 0 &lt; x &lt; 100;
 *   <li>And the size must be 0 &le; x; (0 $rarr; size is calculated from children / variable is a
 *       const
 * </ul>
 */
public class HeapBuilderVariable {
  final String name;
  final int level;
  private int size;
  final HeapVariableType type;
  private final List<HeapBuilderVariable> children = new ArrayList<>();
  final HeapBuilderVariable redefines;

  private char[] defaultValue;

  public int getSize() {
    return this.size;
  }

  List<HeapBuilderVariable> getChildren() {
    return new ArrayList<>(children);
  }

  char[] getDefaultValue() {
    return defaultValue;
  }

  /**
   * Create a new HeapBuilderVariable.
   *
   * @param name The name of the variable.
   * @param level The level of the variable, used to order it correctly (children)
   * @param type The type used to create the correct {@link
   *     com.github.lopoha.coboltruffle.nodes.expression.heap.CobolHeapPointer} instance.
   * @param size The size (number of characters) the variable occupies.
   * @param defaultValue The default value used for initialization of the variable.
   * @param redefines The value it redefines.
   */
  public HeapBuilderVariable(String name,
      int level,
      HeapVariableType type,
      int size,
      char[] defaultValue,
      HeapBuilderVariable redefines) {
    assert level > 0 && level < 100 : "The variable level must be between 1 and 99";
    assert size >= 0 : "The size must be bigger equal 0";
    assert type != null;
    this.name = name == null ? null : name.toLowerCase();
    this.level = level;
    this.size = size;
    this.type = type;
    this.defaultValue = defaultValue;
    this.redefines = redefines;
  }

  /**
   * Create a new HeapBuilderVariable.
   *
   * @param name The name of the variable.
   * @param level The level of the variable, used to order it correctly (children)
   * @param type The type used to create the correct {@link
   *     com.github.lopoha.coboltruffle.nodes.expression.heap.CobolHeapPointer} instance.
   * @param redefines The value it redefines.
   */
  public HeapBuilderVariable(String name,
      int level,
      HeapVariableType type,
      HeapBuilderVariable redefines) {
    assert level > 0 && level < 100 : "The variable level must be between 1 and 99";
    assert type != null;
    this.name = name == null ? null : name.toLowerCase();
    this.level = level;
    this.size = 0;
    this.type = type;
    this.defaultValue = null;
    this.redefines = redefines;
  }

  /**
   * Create a new HeapBuilderVariable.
   *
   * @param level The level of the variable, used to order it correctly (children)
   * @param type The type used to create the correct {@link
   *     com.github.lopoha.coboltruffle.nodes.expression.heap.CobolHeapPointer} instance.
   * @param redefines The value it redefines.
   */
  public HeapBuilderVariable(int level,
      HeapVariableType type,
      HeapBuilderVariable redefines) {
    assert level > 0 && level < 100 : "The variable level must be between 1 and 99";
    assert type != null;
    this.name = null;
    this.level = level;
    this.size = 0;
    this.type = type;
    this.defaultValue = null;
    this.redefines = redefines;
  }

  /**
   * Create a new HeapBuilderVariable.
   *
   * @param level The level of the variable, used to order it correctly (children)
   * @param type The type used to create the correct {@link
   *     com.github.lopoha.coboltruffle.nodes.expression.heap.CobolHeapPointer} instance.
   * @param size The size (number of characters) the variable occupies.
   * @param defaultValue The default value used for initialization of the variable.
   */
  public HeapBuilderVariable(String name,
      int level,
      HeapVariableType type,
      int size,
      char[] defaultValue) {
    assert level > 0 && level < 100 : "The variable level must be between 1 and 99";
    assert size >= 0 : "The size must be bigger equal 0";
    assert type != null;
    this.name = name == null ? null : name.toLowerCase();
    this.level = level;
    this.size = size;
    this.type = type;
    this.defaultValue = defaultValue;
    this.redefines = null;
  }

  /**
   * Create a new HeapBuilderVariable.
   *
   * @param level The level of the variable, used to order it correctly (children)
   * @param type The type used to create the correct {@link
   *     com.github.lopoha.coboltruffle.nodes.expression.heap.CobolHeapPointer} instance.
   * @param size The size (number of characters) the variable occupies.
   * @param defaultValue The default value used for initialization of the variable.
   */
  public HeapBuilderVariable(int level,
      HeapVariableType type,
      int size,
      char[] defaultValue) {
    assert level > 0 && level < 100 : "The variable level must be between 1 and 99";
    assert size >= 0 : "The size must be bigger equal 0";
    assert type != null;
    this.name = null;
    this.level = level;
    this.size = size;
    this.type = type;
    this.defaultValue = defaultValue;
    this.redefines = null;
  }

  void add(HeapBuilderVariable heapVariable) {
    assert heapVariable != null;
    int childLevel = this.children.size() > 0 ? this.children.get(0).level : 0;
    if (childLevel == 0 || childLevel == heapVariable.level) {
      this.children.add(heapVariable);
    } else if (childLevel < heapVariable.level) {
      this.children.get(this.children.size() - 1).add(heapVariable);
    } else {
      throw new RuntimeException("TODO : LEVEL WAS TO HIGH");
    }
  }

  void finalizeVariable() {
    this.children.forEach(HeapBuilderVariable::finalizeVariable);
    checkSetSize();
    checkSetDefaultValue();
  }

  private void checkSetSize() {
    // todo: check that redefines have the same size
    int childSize = this.children.stream()
        .filter(v -> v.type != HeapVariableType.Const && v.redefines == null)
        .mapToInt(v -> v.size)
        .sum();

    if (this.size == 0) {
      if (childSize != 0) {
        this.size = childSize;
      } else {
        throw new CobolVariableSizeMissing(this.name);
      }
    } else if (this.size != childSize && childSize != 0) {
      throw new CobolVariableSizeMismatch(this.name, this.size, childSize);
    }
  }

  private void checkSetDefaultValue() {
    // todo: remove copying...
    if (this.defaultValue == null) {
      final char[] childDefault = new char[size];
      if (this.children.size() == 0) {
        Arrays.fill(childDefault, ' ');
      } else {
        int position = 0;
        for (HeapBuilderVariable child : this.children) {
          if (child.type != HeapVariableType.Const && child.redefines == null) {
            System.arraycopy(child.defaultValue, 0, childDefault, position, child.size);
            position += child.size;
          }
        }
      }
      this.defaultValue = childDefault;
    } //todo: else?


    /*
    final boolean childOnlySpace = childDefault.stream().anyMatch(v -> v != ' ');

    if (this.defaultValue == null && childDefault.size() > 0) {
      if (childDefault.size() != this.size) {
        throw new CobolVariableSizeValueMismatch(this.name, this.size, childDefault.size());
      }
      this.defaultValue = childDefault;
    } else if (this.defaultValue == null) {
      this.defaultValue = IntStream.range(0, this.size)
          .mapToObj(i -> ' ')
          .collect(Collectors.toList());
    } else if (childDefault.size() > 0 && !childOnlySpace) {
      // todo: what should happen here? an exception? is is ok?
    }
     */
  }

  HeapBuilderVariable findVariable(String name) {
    assert name != null;
    if (this.name != null && this.name.equals(name)) {
      return this;
    }
    for (HeapBuilderVariable child : this.children) {
      HeapBuilderVariable found = child.findVariable(name);
      if (found != null) {
        return found;
      }
    }
    return null;
  }
}
