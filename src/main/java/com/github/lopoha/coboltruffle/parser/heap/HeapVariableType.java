package com.github.lopoha.coboltruffle.parser.heap;

// todo: allow floats
public enum HeapVariableType {
  String,
  Number,
  // if nothing is specified.
  None,
  Filler,
  Const,
}
