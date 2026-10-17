/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = (default)false
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = true


*/

package org.checkstyle.suppressionxpathfilter.avoidescapedunicodecharacters;

public class InputXpathAvoidEscapedUnicodeCharactersNonPrintable {
    // violation below 'Unicode escape(s) usage should be avoided.'
    private String unitAbbrev9 = "\u03bcs";
    private String nonPrintableCharacter = "\ufeff";
}
