package com.github.lopoha.coboltruffle.parser.nodes;

import com.github.lopoha.coboltruffle.parser.CobolLanguage;
import com.github.lopoha.coboltruffle.parser.CobolRootNode;
import com.github.lopoha.coboltruffle.parser.runtime.CobolUndefinedNameException;
import com.oracle.truffle.api.frame.VirtualFrame;

public class CobolUndefinedSectionRootNode extends CobolRootNode {
  public CobolUndefinedSectionRootNode(CobolLanguage language, String name) {
    super(language, null, null, null, name);
  }

  @Override
  public Object execute(VirtualFrame frame) {
    throw CobolUndefinedNameException.undefinedFunction(null, getName());
  }
}
