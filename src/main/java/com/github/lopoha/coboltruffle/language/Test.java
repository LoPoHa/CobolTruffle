package com.github.lopoha.coboltruffle.language;

import com.oracle.truffle.api.*;

// todo: don't import everything;
import com.github.lopoha.coboltruffle.parser.*;
import com.github.lopoha.coboltruffle.parser.common.*;
import com.oracle.truffle.api.frame.*;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

// todo: test performance for string, bytearrayoutputstream, ... for the preprocessor -> parser pipeline
//       the parser should call the preprocessor, it is called separatly here for demo purpose;

public class Test {
    public static void main(final String[] args) throws Exception {
        String file = "./teststuff/program/test.cbl";
        Source source = Source.newBuilder(CobolLanguage.ID, new File(file)).build();

        System.exit(executeSource(source, System.in, System.out, new HashMap<>()));
    }

    private static int executeSource(Source source, InputStream in, PrintStream out, Map<String, String> options) {
        Context context;
        PrintStream err = System.err;
        try {
            context = Context.newBuilder(CobolLanguage.ID).in(in).out(out).options(options).build();
        } catch (IllegalArgumentException e) {
            err.println(e.getMessage());
            return 1;
        }
        out.println("== running on " + context.getEngine());

        try {
            Value result = context.eval(source);

            System.out.println(context.getBindings(CobolLanguage.ID).getMemberKeys().size());
            for (String member : context.getBindings(CobolLanguage.ID).getMemberKeys()) {
                System.out.println(member);
            }
            if (context.getBindings(CobolLanguage.ID).getMember("main") == null) {
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
                err.println(ex.getMessage());
            }
            return 1;
        } finally {
            context.close();
        }
    }
}