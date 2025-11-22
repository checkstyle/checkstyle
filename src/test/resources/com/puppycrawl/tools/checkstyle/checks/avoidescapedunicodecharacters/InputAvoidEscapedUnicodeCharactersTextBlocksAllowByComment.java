/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = true
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

public class InputAvoidEscapedUnicodeCharactersTextBlocksAllowByComment {
/** Note that "violation below" comments cannot be on the same line for this config */
    public void multiplyString1() {
        // violation below, 'Unicode escape(s) usage should be avoided.'
        String unitAbbrev2 = "asd\u03bcsasd";
        // violation below, 'Unicode escape(s) usage should be avoided.'
        String unitAbbrev3 = "aBc\u03bcssdf\u03bc";
        // violation below, 'Unicode escape(s) usage should be avoided.'
        String unitAbbrev4 = "\u03bcaBc\u03bcssdf\u03bc";
        String unitAbbrev5 = "\u03bcs"; // Greek letter mu, "s" ok
        // violation below, 'Unicode escape(s) usage should be avoided.'
        String allCharactersEscaped = "\u03bc\u03bc";
    }

    public void multiplyString2() {
        // violation below, 'Unicode escape(s) usage should be avoided.'
        String unitAbbrev2 = """
                asd\u03bcsasd""";
        // violation below, 'Unicode escape(s) usage should be avoided.'
        String unitAbbrev3 = """
                aBc\u03bcssdf\u03bc""";
        // violation below, 'Unicode escape(s) usage should be avoided.'
        String unitAbbrev4 = """
                \u03bcaBc\u03bcssdf\u03bc""";
        String unitAbbrev5 = """
                \u03bcs"""; // Greek letter mu, "s" ok
        // violation below, 'Unicode escape(s) usage should be avoided.'
        String allCharactersEscaped = """
                \u03bc\u03bc""";
    }
}
