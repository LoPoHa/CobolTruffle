load("@rules_java//java:defs.bzl", "java_binary")

java_binary(
    name = "CobolTruffle",
    srcs = glob(["src/main/java/com/example/*.java"]),
    main_class = "com.example.Test",
    #TODO Remove preprocessor
    deps = ["@antlr4_runtime//jar", "//:truffle_api", "//parser/Parser:Parser", "//parser/common:ParserCommon"],
)