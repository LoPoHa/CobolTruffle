package com.github.lopoha.coboltruffle.parser;

// todo: allow floats
public enum HeapVariableType {
  String,
  Number,
  // if nothing is specified.
  None,
  Filler,
}