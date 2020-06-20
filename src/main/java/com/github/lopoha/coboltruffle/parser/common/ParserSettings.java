package com.github.lopoha.coboltruffle.parser.common;

import java.util.ArrayList;
import java.util.List;

public class ParserSettings {
    private final List<String> copySearchPath;
    private final List<String> programSearchPath;
    
    public ParserSettings(List<String> copySearchPath, List<String> programSearchPath) {
        this.copySearchPath = new ArrayList(copySearchPath);
        this.programSearchPath = new ArrayList(programSearchPath);
    }

    public List<String> getCopySearchPath() {
        return new ArrayList<>(this.copySearchPath);
    }

    public List<String> getProgramSearchPath() {
        return new ArrayList<>(this.programSearchPath);
    }
}