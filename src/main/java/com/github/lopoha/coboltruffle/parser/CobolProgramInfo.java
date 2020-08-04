package com.github.lopoha.coboltruffle.parser;

import com.oracle.truffle.api.source.Source;
import java.util.ArrayList;
import java.util.List;

public class CobolProgramInfo {
  private final Source source;
  private final List<String> inputParameter;

  CobolProgramInfo(Source source, List<String> inputParameter) {
    this.source = source;
    this.inputParameter = new ArrayList<>(inputParameter);
  }

  public Source getSource() {
    return source;
  }

  public List<String> getInputParameter() {
    return inputParameter;
  }

  public String getName() {
    return CobolMainParser.getFilenameWithoutExtension(this.source);
  }
}
