load("@rules_antlr//antlr:antlr4.bzl", "antlr")
load("@rules_java//java:defs.bzl", "java_library")

antlr(
    name = "antlr_parser_preprocessor",
    srcs = ["CobolPreprocessor.g4", ],
    package = "com.github.lopoha.coboltruffle.parser.preprocessor",
)

java_library(
    name = "AntlrParserPreprocessor",
    srcs = [":antlr_parser_preprocessor"],
    deps = ["@antlr4_runtime//jar"],
    #visibility = ["//language:__pkg__"],
    visibility = ["//parser/Preprocessor:__pkg__"],
)

antlr(
    name = "antlr_parser",
    srcs = ["Cobol.g4", ],
    package = "com.github.lopoha.coboltruffle.parser",
)

java_library(
    name = "AntlrParser",
    srcs = [":antlr_parser"],
    deps = ["@antlr4_runtime//jar"],
    #visibility = ["//language:__pkg__"],
    visibility = ["//parser/Parser:__pkg__"],
)