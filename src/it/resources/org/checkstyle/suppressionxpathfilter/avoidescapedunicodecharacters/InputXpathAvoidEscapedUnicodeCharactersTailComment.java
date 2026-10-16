/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = true
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

package org.checkstyle.suppressionxpathfilter.avoidescapedunicodecharacters;

public class InputXpathAvoidEscapedUnicodeCharactersTailComment {
    // violation below 'Unicode escape(s) usage should be avoided.'
    private String unitAbbrev9 = "\u03bcs";
    String unitAbbrev = "\u03bcs"; // Greek letter mu, "s"
}
