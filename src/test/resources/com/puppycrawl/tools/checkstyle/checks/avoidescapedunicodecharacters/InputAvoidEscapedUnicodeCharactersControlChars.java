/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = true
allowByTailComment = (default)false
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

public class InputAvoidEscapedUnicodeCharactersControlChars {

    private String unitAbbrev1 = "\u0085";
    private String unitAbbrev2 = "\u009f";
    private String unitAbbrev3 = "\u03bcs";
    // violation above, 'Unicode escape(s) usage should be avoided.'

    public boolean matches(char c) {
        switch (c) {
            case '\u0085':
            case '\u009f':
            case '\u03bc':
                // violation above, 'Unicode escape(s) usage should be avoided.'
                return true;
            default:
                return false;
        }
    }
}
