package com.github.lopoha.coboltruffle.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

// todo: implement cache for loading (or should the caching better happen after?)
class ParserCommonHelper {
  // todo: should the file name be case sensitive? or force it to be e.g. all lower case?
  private static File getFile(String name, List<String> paths) {
    name = name.toLowerCase() + ".cbl";

    for (String path : paths) {
      path = path + "/" + name;
      File file = new File(path);
      System.out.println("[" + path + "]");
      System.out.println(file.exists() ? "true" : "false");
      if (file.isFile()) {
        return file;
      }
    }
    throw new RuntimeException("File " + name + " not found.");
  }

  public static File getCopyFile(String name, ParserSettings settings) {
    return getFile(name, settings.getCopySearchPath());
  }

  public static File getProgramFile(String name, ParserSettings settings) {
    return getFile(name, settings.getProgramSearchPath());
  }
}