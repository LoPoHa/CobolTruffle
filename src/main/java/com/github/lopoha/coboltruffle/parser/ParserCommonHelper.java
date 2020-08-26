package com.github.lopoha.coboltruffle.parser;

import java.io.File;
import java.util.List;

class ParserCommonHelper {
  private static File getFile(String name, List<String> paths) {
    name = name.toLowerCase() + ".cbl";

    for (String path : paths) {
      path = path + "/" + name;
      File file = new File(path);
      if (file.isFile()) {
        return file;
      }
    }
    throw new FileNotFoundException(name);
  }

  public static File getCopyFile(String name, ParserSettings settings) {
    return getFile(name, settings.getCopySearchPath());
  }

  public static File getProgramFile(String name, ParserSettings settings) {
    return getFile(name, settings.getProgramSearchPath());
  }
}
