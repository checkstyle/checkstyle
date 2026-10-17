/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = true
allowByTailComment = (default)false
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

package org.checkstyle.suppressionxpathfilter.avoidescapedunicodecharacters;

public class InputXpathAvoidEscapedUnicodeCharactersControlCharacters {
    // violation below 'Unicode escape(s) usage should be avoided.'
    private String unitAbbrev9 = "\u03bcs";
    private String nonPrintableCharacter = "\u0008";
}
