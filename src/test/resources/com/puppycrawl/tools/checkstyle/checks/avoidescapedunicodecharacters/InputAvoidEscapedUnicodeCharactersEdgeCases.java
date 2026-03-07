/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = (default)false
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

// Edge case testing
package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

public class InputAvoidEscapedUnicodeCharactersEdgeCases {

    // violation below, 'Unicode escape(s) usage should be avoided.'
    char charWithUnicode = '\u03bc';

    // violation below, 'Unicode escape(s) usage should be avoided.'
    String stringWithUnicode = "test\u03bc";

    public void method() {
        // violation below, 'Unicode escape(s) usage should be avoided.'
        String s = "\u03bc";

        /* Block comment with \u03bc */
        // violation below, 'Unicode escape(s) usage should be avoided.'
        String s2 = "\u03bc";
    }
}
