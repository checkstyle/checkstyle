// not compilable with javac, this is a test for parsing failure
package com.puppycrawl.tools.checkstyle.grammar.java21;

public class InputTextBlockParsingFail {

    // This should fail to parse since it is not
    // compilable with javac. Our grammar previously
    // accepted this, but it should not.
    String s = """
            \{}
            """;
}
