/*
LeftCurly
option = NL
ignoreEnums = (default)true
tokens = METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyUnicodeNl {
    void ascii() {}
    void 𝒜() {}
    void emoji() /* 😀 */ {}

    void nonEmpty𝒜() { // violation "'{' at column 22 should be on a new line"
        System.out.println("test");
    }
}
