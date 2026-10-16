/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = (default)false
allowIfAllCharactersEscaped = true
allowNonPrintableEscapes = (default)false


*/

package org.checkstyle.suppressionxpathfilter.avoidescapedunicodecharacters;

public class InputXpathAvoidEscapedUnicodeCharactersAllEscaped {
    private String unitAbbrev9 = "\u03bcs"; // violation 'Unicode escape(s) usage should be avoided.'
    String allCharactersEscaped = "\u03bc\u03bc";
}
