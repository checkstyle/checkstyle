/*
OperatorWrap
option = (default)NL
tokens = ASSIGN,COLON,LAND,LOR,STAR,QUESTION


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

import java.io.*; // Star import here as the check handles all TokenTypes.STAR

class InputOperatorWrapNl
{
    {
        int x1
                = (1 // ok
                * 2) * (1 // ok
                    * 2); // ok
        int x2 = // violation ''=' should be on a new line.'
                1 * // violation ''*' should be on a new line.'
                2 * "" // ok
                    .length();
        int x3 = (2 * 1) * 0 * ( // ok, parens
                1 * 2) * 0;
    }

    public InputOperatorWrapNl() throws IOException {
        label: try (Reader r
                     = null) { // ok
        }
        try (Reader r = // violation ''=' should be on a new line.'
                     null) {
        }
        int x = (1 < 2) ? // violation ''?' should be on a new line.'
            false ? "".substring(0,
                    0).length() : false
                ? 1 // ok
                : 2 : 3; // ok

        for (int value
                : new int[0]) {} // ok
        for (int value : // violation '':' should be on a new line.'
                new int[0]) {}

        int[] a1
                = {}; // ok
        int[] a2 = // violation ''=' should be on a new line.'
                {};
        int[] a3 = { // ok
        };
    }

    void comment(int magic) {
        if (magic == 0x32 // '2'
                || magic == 0x41 // ')' // ok
                || magic == 0x58 // 'X' // ok
        ) {
        }
        if (magic != 0x31 && // violation ''&&' should be on a new line.'
                magic != 0x41 && // violation ''&&' should be on a new line.'
                magic != 0x59 // 'Y'
        ) {
        }
    }

    int a;
    void shortName(int oa) {
        a=oa; // ok
        a+=2; // ok
    }

}
