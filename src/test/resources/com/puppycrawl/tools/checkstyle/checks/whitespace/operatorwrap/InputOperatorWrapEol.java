////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
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
        try (Reader r = // ok
                 null) {
        }
        try (Reader r
                 = null) { // violation
        }
    }

}
