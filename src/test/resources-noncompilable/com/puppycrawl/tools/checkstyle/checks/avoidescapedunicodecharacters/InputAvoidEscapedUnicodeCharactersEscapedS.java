//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

/* Config:
 *
 * allowEscapesForControlCharacters = false
 * allowByTailComment = false
 * allowIfAllCharactersEscaped = true
 * allowNonPrintableEscapes = false
 */
public class InputAvoidEscapedUnicodeCharactersEscapedS {
    String value1 = "\u03bc\t"; // ok
    String value2 = "\u03bc\s"; // ok
    String value3 = "\u03bc\s not all escaped chars"; // violation
    String value31 = "\u03bc\n not all escaped chars"; // violation
    String value4 = """
            \s\s\s\n
            """; // ok
    String value5 = """
            \u03bc\s
            """; // ok
    String value6 = """
            \s\s\s\n not all escaped chars
            """; // violation
    String value7 = """
            \u03bc\s not all escaped chars
            """; // violation
}
