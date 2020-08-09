package com.github.lopoha.coboltruffle.parser;

import static com.github.lopoha.coboltruffle.parser.CobolVariableDefinitionParser.addVariable;

import com.github.lopoha.coboltruffle.heap.HeapBuilder;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser;
import com.github.lopoha.coboltruffle.parser.antlr.CobolParser.VariableDefinitionContext;
import com.oracle.truffle.api.source.Source;
import org.antlr.v4.runtime.tree.ParseTree;



class CobolVariableCopyHelper {
  public static HeapBuilder getHeap(CobolParser.VariableDefinitionCopyContext ctx,
                                    CobolMainParser cobolMainParser) {
    final HeapBuilder heap = new HeapBuilder();
    for (ParseTree child : ctx.children) {
      if (child instanceof VariableDefinitionContext) {
        addVariable((VariableDefinitionContext) child, heap);
      } else if (child instanceof CobolParser.CopyContext) {
        CobolParser.CopyContext copyContext = (CobolParser.CopyContext) child;
        Source copySource
            = cobolMainParser.getCopySource(copyContext.ID().getText());
        HeapBuilder heapBuilder = cobolMainParser.processStorageCopy(copySource);
        heap.add(heapBuilder);
      }
    }
    return heap;
  }
}