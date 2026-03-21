/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = (default)false
allowIfAllCharactersEscaped = true
allowNonPrintableEscapes = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

import java.util.concurrent.TimeUnit;

public class InputAvoidEscapedUnicodeCharacters3 {

        private String unitAbbrev2 = "\u03bcs";
                // violation above, 'Unicode escape(s) usage should be avoided.'
        private String unitAbbrev3 = "\u03bcs";
                // violation above, 'Unicode escape(s) usage should be avoided.'
        private String unitAbbrev4 = "\u03bcs";
                // violation above, 'Unicode escape(s) usage should be avoided.'

        public Object fooString() {
                String unitAbbrev = "Î¼s";
                String unitAbbrev2 = "\u03bcs";
                // violation above, 'Unicode escape(s) usage should be avoided.'
                String unitAbbrev3 = "\u03bcs";
                // violation above, 'Unicode escape(s) usage should be avoided.'
                String fakeUnicode = "asd\tsasd";
                String fakeUnicode2 = "\\u23\\u123i\\u";
                String content = null;
                return "\ufeff" + content; // byte order mark
        }

        public Object fooChar() {
                char unitAbbrev2 = '\u03bc';
                char unitAbbrev3 = '\u03bc'; // Greek letter mu, "s"
                char content = 0;
                return '\ufeff' + content; // byte order mark
        }

        public void multiplyString() {
                String unitAbbrev2 = "asd\u03bcsasd";
                // violation above, 'Unicode escape(s) usage should be avoided.'
                String unitAbbrev3 = "aBc\u03bcssdf\u03bc"; /* Greek letter mu, "s" */
                // violation above, 'Unicode escape(s) usage should be avoided.'
                String unitAbbrev4 = "\u03bcaBc\u03bcssdf\u03bc";
                // violation above, 'Unicode escape(s) usage should be avoided.'
                String allCharactersEscaped = "\u03bc\u03bc";
        }

        private static String abbreviate(TimeUnit unit) {
                switch (unit) {
                case NANOSECONDS:
                        return "ns";
                case MICROSECONDS:
                        return "\u03bcs";
                // violation above, 'Unicode escape(s) usage should be avoided.'
                case MILLISECONDS:
                        return "ms";
                case SECONDS:
                        return "s";
                case MINUTES:
                        return "min";
                case HOURS:
                        return "h";
                case DAYS:
                        return "d";
                default:
                        throw new AssertionError();
                }
        }

                static final String WHITESPACE_TABLE = ""
                                + "\u2002\u3000\r\u0085\u200A\u2005\u2000\u3000\\"
                                + "\u2029\u000B\u3000\u2008\u2003\u205F\u3000\u1680"
                                + "\u0009\u0020\u2006\u2001\u202F\u00A0\u000C\u2009"
                                + "\u3000\u2004\u3000\u3000\u2028\n\u2007\u3000";

              public boolean matches(char c) {
                switch (c) {
                  case '\t':
                  case '\n':
                  case '\013':
                  case '\f':
                  case '\r':
                  case ' ':
                  case '\u0085': // some comment
                  case '\u1680':
                  case '\u2028':
                  case '\u2029':
                  case '\u205f':
                  case '\u3000':
                    return true;
                  case '\u2007':
                    return false;
                  default:
                  return c >= '\u2000' && c <= '\u200a';
              }
         }

        private String unitAbbrev5 = "\u03bcs";
        // violation above, 'Unicode escape(s) usage should be avoided.'
        private String unitAbbrev6 = "\u03bcs";
        // violation above, 'Unicode escape(s) usage should be avoided.'
        private String unitAbbrev7 = "\u03bcs";        /* comment separated by tab */
                // violation above, 'Unicode escape(s) usage should be avoided.'
        private String unitAbbrev8 = "\u03bcs"; /* comment has 2 lines */
                // violation above, 'Unicode escape(s) usage should be avoided.'

        void foo() {
                for (char c = '\u0000'; c < '\uffff'; c++) {
                        if (c == '\u001b' ||     // ESC
                                        c == '\u2014')   // Em-Dash?
                                continue;
                }
        }
        private String unitAbbrev9 = "\u03bcs"; /* comment */ int i;
                // violation above, 'Unicode escape(s) usage should be avoided.'
        private String notAUnicodeEscaped1 = "\\u1234";

        private String notAUnicodeEscaped2 = "\\\\u1234";

        private String onlyEscaped = "\\\u1234";

        private String sumilarToEscapedByB = "b\u1234";
        // violation above, 'Unicode escape(s) usage should be avoided.'
        private String sumilarToEscapedCommentedByB = "b\u1234";
        // violation above, 'Unicode escape(s) usage should be avoided.'
        private String sumilarToEscapedByF = "f\u1234";
        // violation above, 'Unicode escape(s) usage should be avoided.'
        private String sumilarToEscapedCommentedByF = "f\u1234";
        // violation above, 'Unicode escape(s) usage should be avoided.'
        private String sumilarToEscapedByR = "r\u1234";
        // violation above, 'Unicode escape(s) usage should be avoided.'
        private String sumilarToEscapedCommentedByR = "r\u1234";
        // violation above, 'Unicode escape(s) usage should be avoided.'
        private String sumilarToEscapedByN = "n\u1234";
        // violation above, 'Unicode escape(s) usage should be avoided.'
        private String sumilarToEscapedCommentedByN = "n\u1234";
        // violation above, 'Unicode escape(s) usage should be avoided.'
        private String sumilarToEscapedByT = "t\u1234";
        // violation above, 'Unicode escape(s) usage should be avoided.'
        private String sumilarToEscapedCommentedByT = "t\u1234";
        // violation above, 'Unicode escape(s) usage should be avoided.'
        private String validEscapeWithManyUs = "t\uuuuuuuuu1234";
        // violation above, 'Unicode escape(s) usage should be avoided.'
        private String validEscapeWithManyUsCommented = "t\uuuuuuuuu1234";
        // violation above, 'Unicode escape(s) usage should be avoided.'
}
