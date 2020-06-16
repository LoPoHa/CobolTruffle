load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "ParserCommon",
    srcs = glob(["src/main/java/com/github/lopoha/coboltruffle/parser/common/*.java"]),
    deps = [],
    #TODO: remove language
    visibility = ["//language:__pkg__", "//parser/Parser:__pkg__", "//parser/Preprocessor:__pkg__"],
    #visibility = ["//parser:Parser"],
)