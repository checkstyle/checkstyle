/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = (default)false
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

public class InputAvoidEscapedUnicodeCharactersTextBlocks {

    public void multiplyString1() {
        String unitAbbrev2 = "asd\u03bcsasd";
        // violation above, 'Unicode escape(s) usage should be avoided.'
        String unitAbbrev3 = "aBc\u03bcssdf\u03bc";
        // violation above, 'Unicode escape(s) usage should be avoided.'
        String unitAbbrev4 = "\u03bcaBc\u03bcssdf\u03bc";
        // violation above, 'Unicode escape(s) usage should be avoided.'
        String allCharactersEscaped = "\u03bc\u03bc";
        // violation above, 'Unicode escape(s) usage should be avoided.'
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
        // violation below, 'Unicode escape(s) usage should be avoided.'
        String allCharactersEscaped = """
            \u03bc\u03bc""";
    }
}

