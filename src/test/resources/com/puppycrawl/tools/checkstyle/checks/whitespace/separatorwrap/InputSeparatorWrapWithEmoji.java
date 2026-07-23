/*
SeparatorWrap
option = NL
tokens = DOT, COMMA,ELLIPSIS, ARRAY_DECLARATOR, METHOD_REF

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;

import java.util.Arrays;

public class InputSeparatorWrapWithEmoji {
    protected String[] s1 = new String[
        /*🎄 with text */    ] {"aab🎄", "a🎄a👍ba"}; // violation above ''\[' should be on a new line'

    /* emoji👍array */ protected String[] s2 = new String[
        ] {"🥳", "😠", "😨"}; // violation above ''\[' should be on a new line'

    /*👆🏻 👇🏻*/ public void test1(String...
                        parameters) { // violation above ''...' should be on a new line'
    }

    public void test2(String
                          /* 👌🏻👌🏻 */    ...parameters) {
        String s = "ffffooooString";
        /* 🧐🥳 */ s.
            isEmpty(); // violation above ''.' should be on a new line'
        try {
            test2("2", s);
        } catch (Exception e) {}

        test1("1"
            /*🧐 sda 🥳 */   ,s);

    }
    void goodCase() {
        String[] stringArray =
            {
                "🌏", "🌔🌟", "QWERTY", "🧛🏻", "John",
                /*🤞🏻*/ }; // violation above '',' should be on a new line'
        /*🤞🏻 🤞🏻*/ Arrays.sort(stringArray, String::
            compareToIgnoreCase); // violation above ''::' should be on a new line'
    }
}
