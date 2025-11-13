/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = (default)false
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

public class InputAvoidEscapedUnicodeCharactersComments {

    // Test empty block comments
    /* */
    /**/
    /*   */

    String test1 = "hello";
    String test2 = "world";

    /* Regular block comment */
    /** Javadoc comment */

    String multiByteLine = "Text with 😊 emoji and 🌍 world"; /* trailing comment */

    String test5 = "\u03bc"; // violation

    String test6 = "😊\u03bc"; // violation

    /* This comment
       spans multiple
       lines and should
       test the span checking logic */
    String multiLineSpanTest = "\u03bc"; // violation

    String trailingWhitespaceTest = "\u03bc"; /* comment */    // violation

    String shortLine = "\u03bc"; // violation

    /* Comment ends above */
    String noTrailingComment = "\u03bc"; // violation

    /* Comment completely on different line */

    String isolatedLine = "\u03bc"; // violation

    String whitespaceTest = "\u03bc"; /* comment */                                    // violation

 }
