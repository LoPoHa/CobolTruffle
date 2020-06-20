package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.parser.antlr.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.RuntimeException;

// todo: better name (as always...)
// todo: handle level 88
public class HeapBuilderVariable {
    final int level;
    // size = 0 => combined size of all children.
    // todo : size of 0 + children size 0 => error
    private int size;
    final String variableName;
    final HeapVariableType heapVariableType;
    private String value;
    private final List<HeapBuilderVariable> children = new ArrayList<>();
    private int childLevel = 0;
    final String redefines;

    HeapBuilderVariable(int level, String variableName, HeapVariableType heapVariableType, String redefines) {
        this.level = level;
        this.size = 0;
        this.variableName = variableName;
        this.heapVariableType = heapVariableType;
        this.value = null;
        this.redefines = redefines;
    }

    HeapBuilderVariable(int level, String variableName, HeapVariableType heapVariableType, int size, String value) {
        assert size > 0;
        this.level = level;
        this.size = size;
        this.variableName = variableName;
        this.heapVariableType = heapVariableType;
        this.value = value;
        this.redefines = null;
    }

    public String getValue() {
        return this.value;
    }

    // todo: better name
    // todo: create default value from children
    void finalizeHeapBuilder() {
        if (this.children.size() > 0) {
            int childrenSize = 0;
            StringBuilder value = new StringBuilder();
            for (HeapBuilderVariable child : children) {
                if (child.redefines == null) {
                    child.finalizeHeapBuilder();
                    childrenSize += child.size;
                    // child value should never be null, since we set it for all children before adding it. everything else is a bug!
                    assert child.value != null;
                    value.append(child.value);
                } else {
                    // todo: should redefines should checked too if the child sizes match? + if it is the same size as the variable it redefines?
                    //       or should it be checked better checked in cobolheap
                    System.out.println(String.format("TODO: Handle redefines in parser for %s redefining %s", child.variableName, child.redefines));
                    //throw new NotImplementedException();
                }
            }
            if (this.size != 0 && this.size != childrenSize) {
                throw new CobolVariableSizeMismatch(this.variableName, this.size, childrenSize);
            }
            this.size = childrenSize;
            // todo: check if value length is equal to size...
            String defaultValue = value.toString();
            if (defaultValue == null || defaultValue == "") {
                // todo: is the default value for number also space? or is it 0?
                this.value = new String(new char[this.size]).replace('\0', ' ');
            } else if (defaultValue.length() != this.size) {
                throw new NotImplementedException();
            } else {
                this.value = defaultValue;
            }
        } else {
            if (this.value == null) {
                this.value = new String(new char[this.size]).replace('\0', ' ');
            }
        }
    }

    int getSize() {
        return this.size;
    }

    public List<HeapBuilderVariable> getChildren() {
        return new ArrayList<>(this.children);
    }
    

    // todo handle levels
    void add(HeapBuilderVariable heapVariable) {
        assert heapVariable != null;
        if (this.childLevel == 0 || this.childLevel == heapVariable.level) {
            this.childLevel = heapVariable.level;
            this.children.add(heapVariable);
        } else if (this.childLevel < heapVariable.level) {
            this.children.get(this.children.size() - 1).add(heapVariable);
        } else {
            throw new RuntimeException("TODO : LEVEL WAS TO HIGH");
        }
    }


    void prettyPrint(int index) {
        System.out.printf("%s%d %s %s %d %s redefining %s\n",
                             " ".repeat(index),
                             this.level,
                             this.variableName == null ? "" : this.variableName,
                             this.heapVariableType.toString(),
                             this.size,
                             this.value == null ? "" : this.value,
                             this.redefines == null ? "-" : this.redefines);
        for(HeapBuilderVariable child : this.children) {
            child.prettyPrint(index + 2);
        }
    }
}