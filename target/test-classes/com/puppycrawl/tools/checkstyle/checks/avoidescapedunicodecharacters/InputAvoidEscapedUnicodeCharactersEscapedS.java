/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = (default)false
allowIfAllCharactersEscaped = true
allowNonPrintableEscapes = (default)false


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

public class InputAvoidEscapedUnicodeCharactersEscapedS {
    String value1 = "\u03bc\t";
    String value2 = "\u03bc\s";
    String value3 = "\u03bc\s not all escaped chars";
    // violation above, 'Unicode escape(s) usage should be avoided.'
    String value31 = "\u03bc\n not all escaped chars";
    // violation above, 'Unicode escape(s) usage should be avoided.'

    String value4 = """
            \s\s\s\n
            """; // ok, no unicode chars
    String value5 = """
            \u03bc\s"""; // ok, same string as 'value2'
    String value6 = """
            \s\s\s\n not all escaped chars
            """; // ok, no unicode chars
    // violation below, 'Unicode escape(s) usage should be avoided.'
    String value7 = /* violation */"""
            \u03bc\s not all escaped chars
            """;
    // violation below, 'Unicode escape(s) usage should be avoided.'
    String value8 = /* violation */"""
            \u03bc\n not all escaped chars
            """;
    // violation below, 'Unicode escape(s) usage should be avoided.'
    String value9 = /* violation */"""
            l\u03bc\n
            """;
    // violation below, 'Unicode escape(s) usage should be avoided.'
    String value10 = "\n       \u03bc\s";
    String value11 = """
        \u03bc\
        \s\u03bc\
        """;
}
