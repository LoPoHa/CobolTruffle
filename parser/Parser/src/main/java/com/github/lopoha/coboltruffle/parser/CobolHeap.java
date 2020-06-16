package com.github.lopoha.coboltruffle.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CobolHeap {
    private final HashMap<String, HeapPointer> pointerMap = new HashMap<>();

    private final List<Character> heap = new ArrayList<>();

    // todo cleanup + better distinguish between heap and heapbuilder...
    
    // todo bug: a variable can redefine something that was not defined in the same scope. e.g
    // 01 FOO XX.
    // 01 BAR
    //  05 BAZ REDEFINES FOO.
    // ...
    // maybe this should be checked in HeapBuilderVariable when checking the sizes...
    public void addToHeap(HeapBuilder heapBuilder) {
        for (HeapBuilderVariable variable : heapBuilder.getVariables()) {
            if (this.pointerMap.containsKey(variable.variableName.toLowerCase())) {
                // what should happen if it is already defined?
                // nothing? because of the global heap?
            } else {
                final int variableBaseHeapPosition = this.heap.size();
                variable.finalizeHeapBuilder();
                // todo: respect the default value instead of blank...
                //       this should be done in the addVariableToPointerMap function
                final List<Character> variableHeap = Collections.nCopies(variable.getSize(), ' ');
                this.heap.addAll(variableHeap);
                addVariableToPointerMap(variable, variableBaseHeapPosition, true);
            }
        }
    }

    // todo cleanup!!!
    private void addVariableToPointerMap(final HeapBuilderVariable variable, final int variableBasePosition, boolean initialize) {
        // todo should a check if the variable is already defined be here? this time it should be an error? or not?
        if (initialize) {
            HeapPointer basePointer = new HeapPointer(variableBasePosition, variable.getSize(), this.heap, variable.getValue());
            basePointer.initialize();
        }
        if (variable.variableName != null) {
            this.pointerMap.put(variable.variableName.toLowerCase(), new HeapPointer(variableBasePosition, variable.getSize(), this.heap, variable.getValue()));
        }
        
        int variableHeapPosition = variableBasePosition;
        for (HeapBuilderVariable child : variable.getChildren()) {
            if (child.redefines != null) {
                HeapPointer redefinePointer = this.pointerMap.get(child.redefines.toLowerCase());
                if (redefinePointer == null) {
                    throw new CobolUnknownVariableRedefineException(child.redefines, child.variableName);
                }
                addVariableToPointerMap(child, redefinePointer.position, false);
            } else {
                addVariableToPointerMap(child, variableHeapPosition, false);
                variableHeapPosition += child.getSize();
            }
        }

    }

    public HeapPointer getHeapPointer(String variableName) {
        variableName = variableName.toLowerCase();
        if (this.pointerMap.containsKey(variableName)) {
            return this.pointerMap.get(variableName);
        } else {
            throw new CobolVariableNotFoundException(variableName);
        }
    }

    public String getHeap() {
        StringBuilder value = new StringBuilder();
        for (int i = 0; i <  this.heap.size(); i++) {
            value.append(this.heap.get(i));
        }
        return value.toString();
    }
}