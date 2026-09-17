/*
WhitespaceBeforeEmptyBody
tokens = CTOR_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

public class InputWhitespaceBeforeEmptyBodyConstructor {

    // violation below ''{' is not preceded with whitespace'
    InputWhitespaceBeforeEmptyBodyConstructor(){}

    // violation below ''{' is not preceded with whitespace'
    InputWhitespaceBeforeEmptyBodyConstructor(int a){
        // comment
        /* comment */
    }

    InputWhitespaceBeforeEmptyBodyConstructor(int a, int b){
        int c = a + b;
    }

    record Bar(int x) {
        Bar{} // violation ''{' is not preceded with whitespace'
    }
}
