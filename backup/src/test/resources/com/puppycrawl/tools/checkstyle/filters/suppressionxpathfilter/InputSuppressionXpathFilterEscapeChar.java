/*
SuppressionXpathFilter
file = (file)InputSuppressionXpathFilterEscapeChar.xml
optional = (default)false

com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck
format = [^a-zA-z0-9]*
ignoreCase = (default)false
message = (default)
tokens = CHAR_LITERAL

*/
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

public class InputSuppressionXpathFilterEscapeChar {

    char a = '&'; // violation

    char b = '\"'; // violation

    char c = '\''; // filtered violation

    char d = '<'; // filtered violation

    char e = '>'; // filtered violation
}
