/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = (default)false
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

import java.util.concurrent.TimeUnit;

public class InputAvoidEscapedUnicodeCharactersOne {

        private String unitAbbrev2 = "\u03bcs";
        // violation above 'Unicode escape(s) usage should be avoided.'
        private String unitAbbrev3 = "\u03bcs";
        // violation above 'Unicode escape(s) usage should be avoided.'
        private String unitAbbrev4 = "\u03bcs";
        // violation above 'Unicode escape(s) usage should be avoided.'
        public Object fooString() {
                String unitAbbrev = "Î¼s";
                String unitAbbrev2 = "\u03bcs";
                // violation above 'Unicode escape(s) usage should be avoided.'
                String unitAbbrev3 = "\u03bcs";
                // violation above 'Unicode escape(s) usage should be avoided.'
                String fakeUnicode = "asd\tsasd";
                String fakeUnicode2 = "\\u23\\u123i\\u";
                String content = null;
                return "\ufeff" + content;
                // violation above 'Unicode escape(s) usage should be avoided.'
        }

        public Object fooChar() {
                char unitAbbrev2 = '\u03bc';
                // violation above 'Unicode escape(s) usage should be avoided.'
                char unitAbbrev3 = '\u03bc';
                // violation above 'Unicode escape(s) usage should be avoided.'
                char content = 0;
                return '\ufeff' + content;
                // violation above 'Unicode escape(s) usage should be avoided.'
        }

        public void multiplyString() {
                String unitAbbrev2 = "asd\u03bcsasd";
                // violation above 'Unicode escape(s) usage should be avoided.'
                String unitAbbrev3 = "aBc\u03bcssdf\u03bc"; /* Greek letter mu, "s" */
                // violation above 'Unicode escape(s) usage should be avoided.'
                String unitAbbrev4 = "\u03bcaBc\u03bcssdf\u03bc";
                // violation above 'Unicode escape(s) usage should be avoided.'
                String allCharactersEscaped = "\u03bc\u03bc";
                // violation above 'Unicode escape(s) usage should be avoided.'
        }

        private static String abbreviate(TimeUnit unit) {
                switch (unit) {
                case NANOSECONDS:
                        return "ns";
                case MICROSECONDS:
                        return "\u03bcs";
                // violation above 'Unicode escape(s) usage should be avoided.'
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
                // violation above 'Unicode escape(s) usage should be avoided.'
                                + "\u2029\u000B\u3000\u2008\u2003\u205F\u3000\u1680"
                // violation above 'Unicode escape(s) usage should be avoided.'
                                + "\u0009\u0020\u2006\u2001\u202F\u00A0\u000C\u2009"
                // violation above 'Unicode escape(s) usage should be avoided.'
                                + "\u3000\u2004\u3000\u3000\u2028\n\u2007\u3000";
                // violation above 'Unicode escape(s) usage should be avoided.'

              public boolean matches(char c) {
                switch (c) {
                  case '\t':
                  case '\n':
                  case '\013':
                  case '\f':
                  case '\r':
                  case ' ':
                  case '\u0085': // violation 'Unicode escape(s) usage should be avoided.'
                  case '\u1680': // violation 'Unicode escape(s) usage should be avoided.'
                  case '\u2028': // violation 'Unicode escape(s) usage should be avoided.'
                  case '\u2029': // violation 'Unicode escape(s) usage should be avoided.'
                  case '\u205f': // violation 'Unicode escape(s) usage should be avoided.'
                  case '\u3000': // violation 'Unicode escape(s) usage should be avoided.'
                    return true;
                  case '\u2007': // violation 'Unicode escape(s) usage should be avoided.'
                    return false;
                  default:
                  return c >= '\u2000' && c <= '\u200a';
                  // 2 violations above:
                  //             'Unicode escape(s) usage should be avoided.'
                  //             'Unicode escape(s) usage should be avoided.'
              }
         }
}
