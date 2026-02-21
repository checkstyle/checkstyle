/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = true
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

public class InputAvoidEscapedUnicodeCharactersTextBlockWithTrailingComment {

    public void testTextBlockWithTrailingComment() {
        // violation below, 'Unicode escape(s) usage should be avoided.'
        String unitAbbrev1 = """
            asd\u03bcsasd""";
        String unitAbbrev2 = """
            asd\u03bcsasd"""; // Greek letter mu
        // violation below, 'Unicode escape(s) usage should be avoided.'
        String normal = "\u03bc";
    }
}
