package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.parser.antlr.*;
import java.util.ArrayList;
import java.util.List;
//import java.lang.RuntimeException;

// todo: better name (as always...)
// todo: split heapbuilder into heapbuilder and variable class
// todo: handle redefines
// todo: handle arrays (table)
public class HeapBuilder {
    private final List<HeapBuilderVariable> variables = new ArrayList<>();;

    // root level is either 1 or 77 (todo: what does the 77 mean?)
    private boolean isRootLevel(int level) {
        return level == 1 || level == 77;
    }

    // todo handle levels
    void add(HeapBuilderVariable heapVariable) {
        if (isRootLevel(heapVariable.level)) {
            this.variables.add(heapVariable);
        } else {
            // better handle not root values for error messages
            this.variables.get(this.variables.size() - 1).add(heapVariable);
        }
    }


    void prettyPrint() {
        for(HeapBuilderVariable heapVariable : this.variables) {
            heapVariable.prettyPrint(0);
        }
    }

    public List<HeapBuilderVariable> getVariables() {
        return new ArrayList<>(this.variables);
    }
}