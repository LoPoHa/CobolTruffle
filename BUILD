java_library(
    name = "truffle_api",
    exports = [
        "@maven//:org_graalvm_truffle_truffle_api",
    ],
    visibility = [ "//language:__pkg__", "//parser/parser:__pkg__"],
)

java_plugin(
    name = "truffle_dsl_processor_plugin",
    deps = [
        "@maven//:org_graalvm_truffle_truffle_dsl_processor",
        "@maven//:org_graalvm_sdk_graal_sdk",
    ],
    generates_api = True,
    processor_class = "com.oracle.truffle.dsl.processor.InstrumentableProcessor",
    #processor_class = "com.oracle.truffle.dsl.processor.TruffleProcessor",
                      #com.oracle.truffle.dsl.processor
)

java_library(
    name = "truffle_dsl_processor_library",
    exports = [
        "@maven//:org_graalvm_truffle_truffle_tck",
        "@maven//:org_graalvm_sdk_graal_sdk",
        "@maven//:org_graalvm_truffle_truffle_dsl_processor",
    ],
    exported_plugins = [
        ":truffle_dsl_processor_plugin",
    ],
    visibility = [ "//language:__pkg__", "//parser/parser:__pkg__"],
    neverlink = True,
)