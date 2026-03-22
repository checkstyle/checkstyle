/*
com.puppycrawl.tools.checkstyle.filters.SuppressionXpathFilter
file = (file)InputSuppressionXpathFilter6.xml
optional = (default)false

com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck
format = [^a-zA-z0-9]*
ignoreCase = (default)false
message = (default)
tokens = CHAR_LITERAL

*/

package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

public class InputSuppressionXpathFilter6 {

    char a = '&'; // filtered violation 'illegal pattern'
}
