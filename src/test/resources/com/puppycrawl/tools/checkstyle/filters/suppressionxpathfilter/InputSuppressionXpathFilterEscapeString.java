package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

/*
 * Config: Default.
 */
public class InputSuppressionXpathFilterEscapeString {

    String quoteChar = "\"escaped\""; // violation

    String lessChar = "<escaped"; // violation

    String ampersandChar = "&escaped"; // violation

    String greaterChar = ">escaped"; // violation

    String newLineChar = "escaped\n"; // violation

    String specialChar = "escaped\r"; // violation

    String aposChar = "'escaped'"; // violation

    String unicodeCharOne = "语言处理Char"; // violation

    String unicodeCharTwo = "ǯChar"; // violation
}
