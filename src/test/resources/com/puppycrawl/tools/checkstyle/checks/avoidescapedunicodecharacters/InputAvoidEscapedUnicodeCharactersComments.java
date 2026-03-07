/*
AvoidEscapedUnicodeCharacters
allowEscapesForControlCharacters = (default)false
allowByTailComment = true
allowIfAllCharactersEscaped = (default)false
allowNonPrintableEscapes = (default)false


*/

// Test cases for comment handling
package com.puppycrawl.tools.checkstyle.checks.avoidescapedunicodecharacters;

public class InputAvoidEscapedUnicodeCharactersComments {

    public void test1() {
        // Just a comment, no tokens
    }

    public void test2() {
        // violation below, 'Unicode escape(s) usage should be avoided.'
        String s = "\u03bc"; // comment after
    }

    public void test3() {
        // violation below, 'Unicode escape(s) usage should be avoided.'
        String s = "\u03bc";
        // comment on its own line
    }

    /* multi-line comment
       spanning lines
     */
    public void test4() {
        // violation below, 'Unicode escape(s) usage should be avoided.'
        String s = "\u03bc";
    }
}
