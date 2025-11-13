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

    String test5 = "\u03bc"; // violation
}