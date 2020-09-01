package com.github.lopoha.coboltruffle.parser;

import com.github.lopoha.coboltruffle.parser.antlr.CobolBaseListener;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser;
import com.github.lopoha.coboltruffle.parser.heap.HeapBuilder;
import com.oracle.truffle.api.source.Source;

// TODO: Move create separate exceptions instead of reusing runtimeexception!
// todo: cleanup, merge methods, ...
// todo: is there a difference between e.g. no value and value space? or 0?
// todo: should all numbers be handled as floats? and integers are floats with no decimal place?
// todo: multiple classes
// todo: can a copy member don't start at level 01? and the following not declaration start at 01?
//       if not, the preprocessor should put info in, when a copy starts/ends,
//       so this can be checked...
// todo: how should the linkage heap be handled? this could also be implemented as parameters
//       that gets passed in whe calling. (like a constructor)

class CobolStorageCopyListenerImpl extends CobolBaseListener {
  private final CobolMainParser cobolMainParser;
  private final Source source;
  private HeapBuilder heap;

  /**
   * Creates the Listener, that walks the Tokens and creates the coboltruffle classes.
   *
   * @param cobolMainParser a reference to the cobol main parser.
   */
  CobolStorageCopyListenerImpl(CobolMainParser cobolMainParser, Source source) {
    assert cobolMainParser != null;
    assert source != null;
    this.source = source;
    this.cobolMainParser = cobolMainParser;
  }

  @Override
  public void enterVariableDefinitionCopy(CobolParser.VariableDefinitionCopyContext ctx) {
    this.heap = CobolVariableCopyHelper.getHeap(ctx, this.cobolMainParser);
  }

  public HeapBuilder getHeap() {
    return heap;
  }
}
