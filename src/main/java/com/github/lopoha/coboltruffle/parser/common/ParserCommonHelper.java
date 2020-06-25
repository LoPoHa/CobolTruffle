package com.github.lopoha.coboltruffle.parser.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

// todo: implement cache for loading (or should the caching better happen after?)
public class ParserCommonHelper {
  // todo: should the file name be case sensitive? or force it to be e.g. all lower case?
  private static String loadFromFile(String name, List<String> paths) {
    try {
      name = name + ".cbl";

      for (String path : paths) {
        path = path + "/" + name;
        if (new File(path).isFile()) {
          return new String(Files.readAllBytes(Paths.get(path)));
        }
      }
      throw new RuntimeException("File " + name + " not found.");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String loadCopyFromFile(String name, ParserSettings settings) {
    return loadFromFile(name, settings.getCopySearchPath());
  }

  public static String loadProgramFromFile(String name, ParserSettings settings) {
    return loadFromFile(name, settings.getProgramSearchPath());
  }
}