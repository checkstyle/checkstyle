/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = true
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

public class InputAvoidEscapedUnicodeCharacters2Two {

        private String unitAbbrev5 = "\u03bcs";  // comment is separated by space + tab
        private String unitAbbrev6 = "\u03bcs";  // comment is separated by tab
        private String unitAbbrev7 = "\u03bcs";  /* comment is separated by tab */
        private String unitAbbrev8 = "\u03bcs";  /* comment
                                                   has 2 lines */
        void foo() {
                for (char c = '\u0000'; c < '\uffff'; c++) {
                        if (c == '\u001b' ||// 2 violations above:
                                //                 'Unicode escape(s) usage should be avoided.'
                                //                 'Unicode escape(s) usage should be avoided.'

                                        c == '\u2014')   // Em-Dash?
                                continue;
                }
        }
        private String unitAbbrev9 = "\u03bcs"; /* comment */ int i;
        // violation above 'Unicode escape(s) usage should be avoided.'
        private String notAUnicodeEscaped1 = "\\u1234";

        private String notAUnicodeEscaped2 = "\\\\u1234";

        private String onlyEscaped = "\\\u1234";
        // violation above 'Unicode escape(s) usage should be avoided.'
        private String sumilarToEscapedByB = "b\u1234";
        // violation above 'Unicode escape(s) usage should be avoided.'
        private String sumilarToEscapedCommentedByB = "b\u1234"; // comment
        // violation below 'Unicode escape(s) usage should be avoided.'
        private String sumilarToEscapedByF = "f\u1234";
        private String sumilarToEscapedCommentedByF = "f\u1234"; // comment
        // violation below 'Unicode escape(s) usage should be avoided.'
        private String sumilarToEscapedByR = "r\u1234";
        private String sumilarToEscapedCommentedByR = "r\u1234"; // comment
        // violation below 'Unicode escape(s) usage should be avoided.'
        private String sumilarToEscapedByN = "n\u1234";
        private String sumilarToEscapedCommentedByN = "n\u1234"; // comment
        // violation below 'Unicode escape(s) usage should be avoided.'
        private String sumilarToEscapedByT = "t\u1234";
        private String sumilarToEscapedCommentedByT = "t\u1234"; // comment
        // violation below 'Unicode escape(s) usage should be avoided.'
        private String validEscapeWithManyUs = "t\uuuuuuuuu1234";
        private String validEscapeWithManyUsCommented = "t\uuuuuuuuu1234"; // comment
}
