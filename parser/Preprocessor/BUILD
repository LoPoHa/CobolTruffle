load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "ParserPreprocessor",
    srcs = glob(["src/main/java/com/github/lopoha/coboltruffle/parser/preprocessor/*.java"]),
    deps = ["@antlr4_runtime//jar", "//parser/antlr:AntlrParserPreprocessor", "//parser/common:ParserCommon"],
    #TODO: remove language
    visibility = ["//parser/Parser:__pkg__"],
    #visibility = ["//parser:Parser"],
)