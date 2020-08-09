package com.github.lopoha.coboltruffle.helper;

import com.github.lopoha.coboltruffle.CobolLanguage;
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

public class ProgramRun {
  public final RunResult runResult;
  public final String sysout;
  public final String errout;

  private ProgramRun(RunResult runResult, String sysout, String errout) {
    this.runResult = runResult;
    this.sysout = sysout;
    this.errout = errout;
  }

  private static RunResult executeSource(Source source,
                                         PrintStream out,
                                         PrintStream err,
                                         Map<String, String> options) {
    Context context;
    try {
      context = Context.newBuilder(CobolLanguage.ID).out(out)
          .options(options)
          .allowAllAccess(true)
          .build();
    } catch (IllegalArgumentException e) {
      err.println(e.getMessage());
      return RunResult.Failure;
    }

    try {
      Value result = context.eval(source);

      if (!result.isNull()) {
        out.println(result.toString());
      }
      return RunResult.Success;
    } catch (PolyglotException ex) {
      if (ex.isInternalError()) {
        // for internal errors we print the full stack trace
        ex.printStackTrace();
      } else {
        err.println(ex.getMessage());
      }
      return RunResult.Failure;
    } finally {
      context.close(true);
    }
  }

  /**
   * Run a program in the src/test/resources/programs folder.
   * @param fileName the filename + folders with src/test/resources/programs as root.
   * @return the result of the run.
   * @throws IOException if the file was not found.
   */
  public static ProgramRun runProgram(String fileName) throws IOException {
    fileName = "./src/test/resources/programs/" + fileName;
    Source source = Source.newBuilder(CobolLanguage.ID, new File(fileName)).build();
    final ByteArrayOutputStream baosSysOut = new ByteArrayOutputStream();
    final ByteArrayOutputStream baosErrOut = new ByteArrayOutputStream();
    final String utf8 = StandardCharsets.UTF_8.name();
    PrintStream sysOut = new PrintStream(baosSysOut, true, utf8);
    PrintStream errOut = new PrintStream(baosErrOut, true, utf8);
    RunResult result = executeSource(source, sysOut, errOut, new HashMap<>());
    sysOut.close();
    errOut.close();
    return new ProgramRun(result, baosSysOut.toString(utf8), baosErrOut.toString(utf8));
  }
}
