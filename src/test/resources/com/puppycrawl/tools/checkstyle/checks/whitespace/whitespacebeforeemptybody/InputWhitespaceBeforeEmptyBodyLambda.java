/*
WhitespaceBeforeEmptyBody
tokens = LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

public class InputWhitespaceBeforeEmptyBodyLambda {

    interface Foo {
        void foo();
    }

    interface Bar {
        int bar();
    }

    void test() {
        Foo a = () ->{  // violation ''{' is not preceded with whitespace'
            // comment
        };

        Foo b = () ->{  // violation ''{' is not preceded with whitespace'
        };

        Bar c = () -> 0;
    }

}
