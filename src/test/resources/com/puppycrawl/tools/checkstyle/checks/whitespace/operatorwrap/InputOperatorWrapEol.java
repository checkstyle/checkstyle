/*
OperatorWrap
option = EOL
tokens = ASSIGN,COLON,LAND,LOR,STAR,QUESTION


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

import java.io.*; // Star import here as the check handles all TokenTypes.STAR

class InputOperatorWrapEol
{
    {
        int x1 = // ok
                1 * // ok
                2;

        int x2
                = 1 // violation ''=' should be on the previous line.'
                * 2; // violation ''*' should be on the previous line.'

        int x3 = ((2 * 1)
                    ) * 0 * (1 * 2) * 0; // ok, parens
    }

    public InputOperatorWrapEol() throws IOException {
        label: try (Reader r = // ok
                 null) {
        }
        try (Reader r
                 = null) { // violation ''=' should be on the previous line.'
        }
        int x = (1 < 2) ? // ok
                false ? "".substring(0,
                        0).length()
                    : false // violation '':' should be on the previous line.'
                    ? 2 : 3 : 4; // violation ''?' should be on the previous line.'

        for (int value : // ok
                new int[0]) {}
        for (int value
                : new int[0]) {} // violation '':' should be on the previous line.'

        int[] a1 = // ok
                {};
        int[] a2
                = {}; // violation ''=' should be on the previous line.'
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
                || magic == 0x41 // violation ''\|\|' should be on the previous line.'
                || magic == 0x58 // violation ''\|\|' should be on the previous line.'
        ) {
        }
    }

    int a;
    void shortName(int oa) {
        a=oa; // ok
        a+=2; // ok
    }

}
