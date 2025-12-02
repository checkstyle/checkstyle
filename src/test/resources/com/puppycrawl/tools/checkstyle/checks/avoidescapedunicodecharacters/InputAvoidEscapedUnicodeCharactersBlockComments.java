/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = true
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

public class InputAvoidEscapedUnicodeCharactersBlockComments {

    String s1 = "\u03bc"; /* multi
                              line */

    String s2 = "\u03bc"; /* block */ // single

    String s3 = "\u03bc"; /* first */ /* second */

    String s4 = "\u03bc"; /* comment */ int x = 5;
    // violation above, 'Unicode escape(s) usage should be avoided.'

    String s5 = "\u03bc"; /* trailing */

    String s6 = "\u03bc";
    // violation above, 'Unicode escape(s) usage should be avoided.'

    String s7 = "\u03bc"; /**/

    String s8 = """
        \u03bc
        """; /* multi
                line */

    String s10 = "\u03bc1"; String s11 = "\u03bc2"; /* comment */

    String s12 = "\u03bc"; /* not trailing */ String moreCode = "test";
    // violation above, 'Unicode escape(s) usage should be avoided.'

    String s13 = "\u03bc"; /* comment */
    String nextOnDifferentLine = "test";

    String s14 = "\u03bc"; /* starts
                               on one line
                               and definitely ends
                               on a later line */

    String s15 = "\u03bc"; /* final before brace */;
    // violation above, 'Unicode escape(s) usage should be avoided.'
}
