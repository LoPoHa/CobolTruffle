package com.github.lopoha.coboltruffle;

public class CobolInternalException extends RuntimeException {
  public CobolInternalException(Exception e) {
    super(e);
  }

  public CobolInternalException() {
    super();
  }

  public CobolInternalException(String message) {
    super(message);
  }
}
