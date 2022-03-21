/*
SuppressionXpathFilter
file = (file)InputSuppressionXpathFilterEscapeChar.xml
optional = (default)false

com.puppycrawl.tools.checkstyle.checks.AvoidEscapedUnicodeCharactersCheck
allowEscapesForControlCharacters = (default)false
allowByTailComment = (default)false
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false

*/
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

public class InputSuppressionXpathFilterEscapeChar {

    char escapedChar1 = '\u03bc'; // filtered violation

    char escapedChar2 = '\u0055'; // filtered violation

    char escapedChar3 = '\u041a'; // violation
}
