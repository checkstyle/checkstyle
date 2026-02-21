/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = true
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

public class InputAvoidEscapedUnicodeCharactersCommentEnd {
/** JavaDoc for field */
    String s1 = "\u221e"; /* trailing comment */

    /** JavaDoc for method */
    public void method() {
        String s2 = "\u221e"; /* trailing in method */
    }

    String s3 = "\u221e"; /* trailing after method */
}

/* Block comment after class */

/** JavaDoc comment at end of file */
