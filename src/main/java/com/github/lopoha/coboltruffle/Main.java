package com.github.lopoha.coboltruffle;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class Main {

  /**
   * Entry point.
   *
   * @param args args.
   * @throws Exception TODO
   */
  public static void main(final String[] args) throws Exception {
    String file = "./teststuff/program/test.cbl";
    Source source = Source.newBuilder(CobolLanguage.ID, new File(file)).build();
    String sysoutFile = "./teststuff/sysout.txt";
    try (PrintStream ps = new PrintStream(sysoutFile, StandardCharsets.UTF_8)) {
      System.exit(executeSource(source, System.in, ps, new HashMap<>()));
    }
  }

  private static int executeSource(
      Source source, InputStream in, PrintStream out, Map<String, String> options) {
    Context context;
    PrintStream err = System.err;
    try {
      context =
          Context.newBuilder(CobolLanguage.ID)
              .in(in)
              .out(out)
              .options(options)
              .allowAllAccess(true) // because of includes
              .build();
    } catch (IllegalArgumentException e) {
      err.println(e.getMessage());
      return 1;
    }
    //out.println("== running on " + context.getEngine());

    try {
      Value result = context.eval(source);

      // name must be the file name
      if (context.getBindings(CobolLanguage.ID).getMember("test") == null) {
        err.println("No function main defined in Cobol source file. ->  internal error");
        return 1;
      }
      if (!result.isNull()) {
        out.println(result.toString());
      }
      return 0;
    } catch (PolyglotException ex) {
      if (ex.isInternalError()) {
        // for internal errors we print the full stack trace
        ex.printStackTrace();
      } else {
        ex.printStackTrace();
        err.println(ex.getMessage());
      }
      return 1;
    } finally {
      context.close();
    }
  }
}
