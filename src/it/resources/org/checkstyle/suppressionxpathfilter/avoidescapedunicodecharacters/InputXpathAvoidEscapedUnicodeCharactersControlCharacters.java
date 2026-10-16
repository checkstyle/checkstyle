/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = true
allowByTailComment = (default)false
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

package org.checkstyle.suppressionxpathfilter.avoidescapedunicodecharacters;

public class InputXpathAvoidEscapedUnicodeCharactersControlCharacters {
    private String unitAbbrev9 = "\u03bcs"; // violation 'Unicode escape(s) usage should be avoided.'
    private String nonPrintableCharacter = "\u0008";
}
