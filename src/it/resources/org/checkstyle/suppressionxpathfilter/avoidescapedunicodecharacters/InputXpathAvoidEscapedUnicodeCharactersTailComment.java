/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = true
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

package org.checkstyle.suppressionxpathfilter.avoidescapedunicodecharacters;

public class InputXpathAvoidEscapedUnicodeCharactersTailComment {
    private String unitAbbrev9 = "\u03bcs";
    // violation above 'Unicode escape(s) usage should be avoided.'
    String unitAbbrev = "\u03bcs"; // Greek letter mu, "s"
}
