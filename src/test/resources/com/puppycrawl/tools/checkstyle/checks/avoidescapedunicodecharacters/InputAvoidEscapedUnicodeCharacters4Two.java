/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = (default)false
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = true


*/

package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

import java.util.concurrent.TimeUnit;

public class InputAvoidEscapedUnicodeCharacters4Two {

        private String unitAbbrev5 = "\u03bcs";         // violation
        private String unitAbbrev6 = "\u03bcs";        // violation
        private String unitAbbrev7 = "\u03bcs";        /* comment separated by tab */ // violation
        private String unitAbbrev8 = "\u03bcs"; /* comment // violation
                                                   has 2 lines */
        void foo() {
                for (char c = '\u0000'; c < '\uffff'; c++) {
                        if (c == '\u001b' ||     // violation
                                        c == '\u2014')   // violation
                                continue;
                }
        }
        private String unitAbbrev9 = "\u03bcs"; /* comment */ int i; // violation

        private String notAUnicodeEscaped1 = "\\u1234";

        private String notAUnicodeEscaped2 = "\\\\u1234";

        private String onlyEscaped = "\\\u1234"; // violation

        private String sumilarToEscapedByB = "b\u1234"; // violation
        private String sumilarToEscapedCommentedByB = "b\u1234"; // violation
        private String sumilarToEscapedByF = "f\u1234"; // violation
        private String sumilarToEscapedCommentedByF = "f\u1234"; // violation
        private String sumilarToEscapedByR = "r\u1234"; // violation
        private String sumilarToEscapedCommentedByR = "r\u1234"; // violation
        private String sumilarToEscapedByN = "n\u1234"; // violation
        private String sumilarToEscapedCommentedByN = "n\u1234"; // violation
        private String sumilarToEscapedByT = "t\u1234"; // violation
        private String sumilarToEscapedCommentedByT = "t\u1234"; // violation
        private String validEscapeWithManyUs = "t\uuuuuuuuu1234"; // violation
        private String validEscapeWithManyUsCommented = "t\uuuuuuuuu1234"; // violation
}
