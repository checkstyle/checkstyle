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
                2;

        int x3 = (2 * 1) * 0 * ( // ok, parens
                1 * 2) * 0;
    }

    public InputOperatorWrapNl() throws IOException {
        try (Reader r
                     = null) { // ok
        }
        try (Reader r = // violation
                     null) {
        }
    }

}
