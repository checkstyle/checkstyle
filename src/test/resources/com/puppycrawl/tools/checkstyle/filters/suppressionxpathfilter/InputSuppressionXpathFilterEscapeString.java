/*
SuppressionXpathFilter
file = (file)InputSuppressionXpathFilterEscapeString.xml
optional = (default)false

com.puppycrawl.tools.checkstyle.checks.AvoidEscapedUnicodeCharactersCheck
allowEscapesForControlCharacters = (default)false
allowByTailComment = (default)false
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false

*/
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

public class InputSuppressionXpathFilterEscapeString {

    String escapedString1 = "\u03bcs"; // filtered violation

    String escapedString2 = "\u0055"; // violation

    String escapedString3 = "\u041a"; // filtered violation
}
