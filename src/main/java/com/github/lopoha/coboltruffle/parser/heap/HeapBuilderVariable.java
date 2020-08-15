package com.github.lopoha.coboltruffle.parser.heap;

import java.util.ArrayList;
import java.util.List;

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
  private final int level;
  private final int size;
  private final HeapVariableType type;
  private final List<HeapBuilderVariable> childs = new ArrayList<>();

  private List<Character> defaultValue;

  /**
   * Create a new HeapBuilderVariable.
   *
   * @param level The level of the variable, used to order it correctly (children)
   * @param size The size (number of characters) the variable occupies.
   * @param type The type used to create the correct {@link
   *     com.github.lopoha.coboltruffle.nodes.expression.heap.CobolHeapPointer} instance.
   */
  public HeapBuilderVariable(int level, int size, HeapVariableType type) {
    assert level > 0 && level < 100 : "The variable level must be between 1 and 99";
    assert size >= 0 : "The size must be bigger equal 0";
    assert type != null;
    this.level = level;
    this.size = size;
    this.type = type;
  }

  /**
   * Create a new HeapBuilderVariable.
   *
   * @param level The level of the variable, used to order it correctly (children)
   * @param type The type used to create the correct {@link
   *     com.github.lopoha.coboltruffle.nodes.expression.heap.CobolHeapPointer} instance.
   */
  public HeapBuilderVariable(int level, HeapVariableType type) {
    assert level > 0 && level < 100 : "The variable level must be between 1 and 99";
    assert type != null;
    this.level = level;
    this.size = 0;
    this.type = type;
  }

  void addChild(HeapBuilderVariable child) {
    assert child.level < this.level : "The child level must be bigger than the current level";
    HeapBuilderVariable lastChild =
        this.childs.size() > 0 ? this.childs.get(this.childs.size() - 1) : null;

    if (lastChild == null || lastChild.level == child.level) {
      this.childs.add(child);
    } else {
      lastChild.addChild(child);
    }
  }
}
