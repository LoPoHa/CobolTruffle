load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "Parser",
    srcs = glob(["src/main/java/com/github/lopoha/coboltruffle/parser/*.java"]),
    deps = [ "@antlr4_runtime//jar",
             "//parser/antlr:AntlrParser",
             "//parser/Preprocessor:ParserPreprocessor",
             "//parser/common:ParserCommon",
             "//:truffle_api",
             "//:truffle_dsl_processor_library",
           ],
    #TODO: remove language
    visibility = ["//language:__pkg__"],
    #visibility = ["//parser:Parser"],
)