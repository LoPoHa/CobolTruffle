# Cobol Truffle

A test to implement an opinionated Cobol on top of Truffle to run in on the jvm.

Not much is implemented at the moment and the code is in a poor state and all over the place.

## Requirements

- Bazel (tested with version 3.2.0) (TODO: Bazelisk + check in bazel version)


There is currently a problem building:
```
cobol_truffle/parser/antlr/BUILD:4:6: Processing ANTLR 4 grammars failed (Exit 1)
Exception in thread "main" java.lang.IllegalStateException: Could not find matching grammar for CobolLexer.interp
        at org.antlr.bazel.AntlrRules.findGrammar(AntlrRules.java:620)
        at org.antlr.bazel.AntlrRules.access$100(AntlrRules.java:39)
        at org.antlr.bazel.AntlrRules$1.visitFile(AntlrRules.java:346)
        at org.antlr.bazel.AntlrRules$1.visitFile(AntlrRules.java:324)
        at java.base/java.nio.file.Files.walkFileTree(Files.java:2724)
        at java.base/java.nio.file.Files.walkFileTree(Files.java:2796)
        at org.antlr.bazel.AntlrRules.generate(AntlrRules.java:323)
        at org.antlr.bazel.AntlrRules.main(AntlrRules.java:99)
```

If this occurs, you have to run `bazel build //parser/antlr:antlr_parser_preprocessor`.
After that, everything should work as expected.