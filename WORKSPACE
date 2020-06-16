load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

### ANTLR4 Parser generator

http_archive(
    name = "rules_antlr",
    sha256 = "26e6a83c665cf6c1093b628b3a749071322f0f70305d12ede30909695ed85591",
    strip_prefix = "rules_antlr-0.5.0",
    urls = ["https://github.com/marcohu/rules_antlr/archive/0.5.0.tar.gz"],
)

load("@rules_antlr//antlr:repositories.bzl", "rules_antlr_dependencies")
rules_antlr_dependencies("4.8")



### Maven dependencies

RULES_JVM_EXTERNAL_TAG = "3.2"
RULES_JVM_EXTERNAL_SHA = "82262ff4223c5fda6fb7ff8bd63db8131b51b413d26eb49e3131037e79e324af"

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    sha256 = RULES_JVM_EXTERNAL_SHA,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "org.graalvm.truffle:truffle-api:20.1.0",
        "org.graalvm.truffle:truffle-dsl-processor:20.1.0",
        "org.graalvm.truffle:truffle-tck:20.1.0",
        "org.graalvm.sdk:graal-sdk:20.1.0",
        "org.graalvm.sdk:polyglot-tck:20.1.0",
        # https://groups.google.com/forum/#!topic/bazel-discuss/piZKdiomXEc
        "xml-apis:xml-apis:1.4.01",
        "junit:junit-dep:4.9",
    ],
    # https://groups.google.com/forum/#!topic/bazel-discuss/piZKdiomXEc
    version_conflict_policy = "pinned",
    repositories = [
        # Private repositories are supported through HTTP Basic auth
        "https://jcenter.bintray.com/",
        "https://maven.google.com",
        "https://repo1.maven.org/maven2",
    ],
)