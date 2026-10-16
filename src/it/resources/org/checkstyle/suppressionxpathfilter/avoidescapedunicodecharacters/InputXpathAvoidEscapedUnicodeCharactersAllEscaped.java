/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = (default)false
allowIfAllCharactersEscaped = true
allowNonPrintableEscapes = (default)false


*/

package org.checkstyle.suppressionxpathfilter.avoidescapedunicodecharacters;

public class InputXpathAvoidEscapedUnicodeCharactersAllEscaped {
    // violation below 'Unicode escape(s) usage should be avoided.'
    private String unitAbbrev9 = "\u03bcs";
    String allCharactersEscaped = "\u03bc\u03bc";
}
