/*
WhitespaceBeforeEmptyBody
tokens = LITERAL_NEW


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

public class InputWhitespaceBeforeEmptyBodyAnonymousClass {

    interface Foo { }

    interface Bar { }

    void method() {

        Foo a = new Foo(){
            int num = 1;
        };

        Foo b = new Foo(){ // violation ''{' is not preceded with whitespace'
            /* comment */
        };

        Foo c = new Foo(){ // violation ''{' is not preceded with whitespace'
        };

        Bar d = new Bar(){ // violation ''{' is not preceded with whitespace'
        };

        Bar e = new Bar(){}; // violation ''{' is not preceded with whitespace'
    }
}
