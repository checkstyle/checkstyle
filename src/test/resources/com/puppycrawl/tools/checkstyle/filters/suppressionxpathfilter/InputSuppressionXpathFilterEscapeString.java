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

    String quoteChar = "\"escaped\""; // filtered violation

    String lessChar = "<escaped"; // filtered violation

    String ampersandChar = "&escaped"; // filtered violation

    String greaterChar = ">escaped"; // filtered violation

    String newLineChar = "escaped\n"; // filtered violation

    String specialChar = "escaped\r"; // filtered violation

    String aposChar = "'escaped'"; // filtered violation

    String unicodeCharOne = "语言处理Char"; // violation

    String unicodeCharTwo = "ǯChar"; // violation
}
