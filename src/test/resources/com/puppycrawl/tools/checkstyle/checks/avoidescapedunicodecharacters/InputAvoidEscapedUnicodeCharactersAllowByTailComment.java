/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = true
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

public class InputAvoidEscapedUnicodeCharactersAllowByTailComment {

    String s1 = "\u03bc"; // comment
    String s2 = "\u03bc";
    // violation above, 'Unicode escape(s) usage should be avoided.'

    String s3 = "\u03bc"; /* comment */
    String s4 = "\u03bc"; /* multi
                             line */

    String s5 = "\u03bc"; /* comment */ int x = 5;
    // violation above, 'Unicode escape(s) usage should be avoided.'

    String s6 = "\u03bc"; /* first */ /* second */
    String s7 = "\u03bc"; /* block */ // single

    String s8 = """
        \u03bc
        """; // comment

    String s9 = """
        \u03bc
        """;
        // violation 3 lines above 'Unicode escape(s) usage should be avoided.'

    // comment before literal
    String s10 = "\u03bc";
    // violation above, 'Unicode escape(s) usage should be avoided.'

    String s11 = "\u03bc"; /* trailing block comment */

    String s12 = /* comment */ "\u03bc";
    // violation above, 'Unicode escape(s) usage should be avoided.'
}
