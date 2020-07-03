package com.github.lopoha.coboltruffle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.Test;

class PlaceholderIT {

  @Test
  void helloWorld() throws IOException {
    //simple hello world
    String file = "./src/test/resources/programs/helloworld.cbl";
    Source source = Source.newBuilder(CobolLanguage.ID, new File(file)).build();
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    final String utf8 = StandardCharsets.UTF_8.name();
    try (PrintStream ps = new PrintStream(baos, true, utf8)) {
      executeSource(source, ps, new HashMap<>());
    }
    String data = baos.toString(utf8);
    assertEquals(String.format("HELLO WORLD%n"), data);
  }

  private static int executeSource(Source source,
                                   PrintStream out,
                                   Map<String, String> options) {
    Context context;
    PrintStream err = System.err;
    try {
      context = Context.newBuilder(CobolLanguage.ID).out(out)
                                                    .options(options)
                                                    .allowAllAccess(true)
                                                    .build();
    } catch (IllegalArgumentException e) {
      err.println(e.getMessage());
      return 1;
    }

    try {
      Value result = context.eval(source);

      if (!result.isNull()) {
        out.println(result.toString());
      }
      return 0;
    } catch (PolyglotException ex) {
      if (ex.isInternalError()) {
        // for internal errors we print the full stack trace
        ex.printStackTrace();
      } else {
        err.println(ex.getMessage());
      }
      return 1;
    } finally {
      context.close();
    }
  }
}
