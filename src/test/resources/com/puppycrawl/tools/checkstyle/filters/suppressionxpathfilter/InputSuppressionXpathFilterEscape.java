package com.puppycrawl.tools.checkstyle.filters.suppressionxpathfilter;

/**
 * Config: default.
 */
public class InputSuppressionXpathFilterEscape {

    String quoteChar = "\"escaped\"" +
            "2"; // violation

    String lessChar = "<escaped" +
            "3"; // violation

    String ampersandChar = "&escaped" +
            "4"; // violation

    String greaterChar = ">escaped" +
            "5"; // violation

    String newLineChar = "escaped\n" +
            "6"; // violation

    String specialChar = "escaped\r" +
            "6"; // violation

    String aposChar = "'escaped'" +
            "7"; // violation
}
