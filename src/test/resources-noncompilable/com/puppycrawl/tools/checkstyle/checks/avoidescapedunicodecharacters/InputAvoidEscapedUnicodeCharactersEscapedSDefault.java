//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

/* Config:
 *
 * default
 */
public class InputAvoidEscapedUnicodeCharactersEscapedSDefault {
    String value1 = "\u03bc\t"; // violation
    String value2 = "\u03bc\s"; // violation
    String value3 = "\u03bc\s not all escaped chars"; // violation
    String value31 = "\u03bc\n not all escaped chars"; // violation
    String value4 = """
            \s\s\s\n
            """; // ok, no unicode chars
    String value5 = """
            \u03bc\s"""; // violation on line 16
    String value6 = """
            \s\s\s\n not all escaped chars
            """; // ok, no unicode chars
    String value7 = """
            \u03bc\s not all escaped chars
            """; // violation on line 21
    String value8 = """
            \u03bc\n not all escaped chars
            """; // violation on line 24
    String value9 = """
            "\u03bc\n"""; // violation on line 27
    String value10 = "\n       \u03bc\s" // violation
}
