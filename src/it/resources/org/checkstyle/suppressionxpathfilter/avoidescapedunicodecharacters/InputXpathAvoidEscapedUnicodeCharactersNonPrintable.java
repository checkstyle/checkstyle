/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = (default)false
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = true


*/

package org.checkstyle.suppressionxpathfilter.avoidescapedunicodecharacters;

public class InputXpathAvoidEscapedUnicodeCharactersNonPrintable {
    private String unitAbbrev9 = "\u03bcs"; // violation 'Unicode escape(s) usage should be avoided.'
    private String nonPrintableCharacter = "\ufeff";
}
