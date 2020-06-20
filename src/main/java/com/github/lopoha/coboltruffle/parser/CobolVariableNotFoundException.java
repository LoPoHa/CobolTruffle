package com.github.lopoha.coboltruffle.parser;

public class CobolVariableNotFoundException extends RuntimeException {
    public CobolVariableNotFoundException(String variableName) {
        // should this print all available variables / search for typos?
        super(String.format("The variable %s was not defined beforehand. ", variableName));
    }
    
}