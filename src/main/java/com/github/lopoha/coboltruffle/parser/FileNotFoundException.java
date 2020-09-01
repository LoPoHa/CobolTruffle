package com.github.lopoha.coboltruffle.parser;

class FileNotFoundException extends RuntimeException {
  FileNotFoundException(String fileName) {
    super("File " + fileName + " not found.");
  }
}
