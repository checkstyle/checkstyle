/*
SeparatorWrap
tokens = COMMA
option = nl

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;

public class InputSeparatorWrapForTestComma2 {

    String s;

    // violation below '',' should be on a new line.'
    int a,  
            b;

    // violation below '',' should be on a new line.'
    public void foo(int a,  
                    int b) {
        int r
            , t; // OK
    }

    public void bar(int p
            , int q) {  // OK
    }
}
