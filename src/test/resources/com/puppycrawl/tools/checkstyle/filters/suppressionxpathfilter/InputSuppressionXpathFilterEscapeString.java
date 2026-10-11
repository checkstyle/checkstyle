/*
SuppressionXpathFilter
file = (file)InputSuppressionXpathFilterEscapeString.xml
optional = (default)false

com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck
format = [^a-zA-z0-9]*
ignoreCase = (default)false
message = (default)
tokens = STRING_LITERAL

*/
package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

public class InputSuppressionXpathFilterEscapeString {

    String quoteChar = "\"escaped\""; // filtered violation 'illegal pattern'

    String lessChar = "<escaped"; // filtered violation 'illegal pattern'

    String ampersandChar = "&escaped"; // filtered violation 'illegal pattern'

    String greaterChar = ">escaped"; // filtered violation 'illegal pattern'

    String newLineChar = "escaped\n"; // filtered violation 'illegal pattern'

    String specialChar = "escaped\r"; // filtered violation 'illegal pattern'

    String aposChar = "'escaped'"; // filtered violation 'illegal pattern'

    String unicodeCharOne = "语言处理Char"; // violation

    String unicodeCharTwo = "ǯChar"; // violation
}
