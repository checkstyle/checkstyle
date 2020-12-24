package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

import java.io.*; // Star import here as the check handles all TokenTypes.STAR

/**
 * Config:
 * option = NL
 **/
class InputOperatorWrapNl
{
    {
        int x1
                = (1 // ok
                * 2) * (1 // ok
                    * 2); // ok
        int x2 = // violation
                1 * // violation
                2 * "" // ok
                    .length();
        int x3 = (2 * 1) * 0 * ( // ok, parens
                1 * 2) * 0;
    }

    public InputOperatorWrapNl() throws IOException {
        label: try (Reader r
                     = null) { // ok
        }
        try (Reader r = // violation
                     null) {
        }
        int x = (1 < 2) ? // violation
            false ? "".substring(0,
                    0).length() : false
                ? 1 // ok
                : 2 : 3; // ok

        for (int value
                : new int[0]) {} // ok
        for (int value : // violation
                new int[0]) {}

        int[] a1
                = {}; // ok
        int[] a2 = // violation
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
        if (magic != 0x31 && // '1'     // violation
                magic != 0x41 && // ')' // violation
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
