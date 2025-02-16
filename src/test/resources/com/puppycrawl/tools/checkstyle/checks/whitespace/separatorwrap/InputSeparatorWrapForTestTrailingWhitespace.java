/*
SeparatorWrap
tokens = COMMA
option = nl

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;

public class InputSeparatorWrapForTestTrailingWhitespace {

    String s;
    
    // Trailing whitespace after ',' in variable declaration
    // violation below '',' should be on a new line.'
    int a,  
            b;

    // Trailing whitespace after ',' in function arguments
    // violation below '',' should be on a new line.'
    public void foo(int a,  
                    int b) {
        int r
            , t;
    }

    public void bar(int p
            , int q) {
    }
}
