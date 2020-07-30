package com.github.lopoha.coboltruffle.parser;


import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.source.Source;
import java.util.ArrayList;
import java.util.List;

public class CobolProgramInfo {
  private final String path;
  private final Source source;
  private final List<String> inputParameter;
  private final RootCallTarget constructor;

  CobolProgramInfo(String path,
                   Source source,
                   List<String> inputParameter,
                   RootCallTarget constructor) {
    this.path = path;
    this.source = source;
    this.inputParameter = new ArrayList<>(inputParameter);
    this.constructor = constructor;
  }

  public String getPath() {
    return path;
  }

  public Source getSource() {
    return source;
  }

  public List<String> getInputParameter() {
    return inputParameter;
  }

  public RootCallTarget getConstructor() {
    return constructor;
  }
}
