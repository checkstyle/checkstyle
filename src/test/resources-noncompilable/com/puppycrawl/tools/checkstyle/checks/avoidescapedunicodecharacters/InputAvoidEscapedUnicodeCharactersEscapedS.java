/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = (default)false
allowIfAllCharactersEscaped = true
allowNonPrintableEscapes = (default)false


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

public class InputAvoidEscapedUnicodeCharactersEscapedS {
    String value1 = "\u03bc\t"; // ok
    String value2 = "\u03bc\s"; // ok
    String value3 = "\u03bc\s not all escaped chars"; // violation
    String value31 = "\u03bc\n not all escaped chars"; // violation
    String value4 = """
            \s\s\s\n
            """; // ok, no unicode chars
    String value5 = """
            \u03bc\s"""; // ok, same string as 'value2'
    String value6 = """
            \s\s\s\n not all escaped chars
            """; // ok, no unicode chars // violation below
    String value7 = /* violation */"""
            \u03bc\s not all escaped chars
            """; // violation below
    String value8 = /* violation */"""
            \u03bc\n not all escaped chars
            """; // violation below
    String value9 = /* violation */"""
            l\u03bc\n
            """;
    String value10 = "\n       \u03bc\s"; // violation
    String value11 = """
        \u03bc\
        \s\u03bc\
        """;
}
