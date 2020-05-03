package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

import java.io.*; // Star import here as the check handles all TokenTypes.STAR

/**
 * Config:
 * option = EOL
 **/
class InputOperatorWrapEol
{
    {
        int x1 = // ok
                1 * // ok
                2;

        int x2
                = 1 // violation
                * 2; // violation

        int x3 = ((2 * 1)
                    ) * 0 * (1 * 2) * 0; // ok, parens
    }

    public InputOperatorWrapEol() throws IOException {
        label: try (Reader r = // ok
                 null) {
        }
        try (Reader r
                 = null) { // violation
        }
        int x = (1 < 2) ? // ok
                false ? "".substring(0,
                        0).length()
                    : false // violation
                    ? 2 : 3 : 4; // violation

        for (int value : // ok
                new int[0]) {}
        for (int value
                : new int[0]) {} // violation

        int[] a1 = // ok
                {};
        int[] a2
                = {}; // violation
        int[] a3 = { // ok
        };
    }

    void comment(int magic) {
        if (magic != 0x31 && // '1' // ok
                magic != 0x41 && // ')' // ok
                magic != 0x59 // 'Y'
            ) {
        }
        if (magic == 0x32 // '2'
                || magic == 0x41 // ')' // violation
                || magic == 0x58 // 'X' // violation
        ) {
        }
    }

    int a;
    void shortName(int oa) {
        a=oa; // ok
        a+=2; // ok
    }

}
