//non-compiled with javac, we should fail to parse this file
package com.puppycrawl.tools.checkstyle.grammar.java21;

public class InputTextBlockParsingFail {

    // This should fail to parse since it is not
    // compilable with javac. Our grammar previously
    // accepted this, but it should not.
    String s = """
            \{}
            """;
}
