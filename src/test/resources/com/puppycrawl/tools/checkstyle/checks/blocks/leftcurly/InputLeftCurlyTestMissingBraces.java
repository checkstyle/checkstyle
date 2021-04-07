////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

/**
 * Config: default
 *
 * Test case for correct use of braces.
 * @author Oliver Burn
 **/
class InputLeftCurlyTestMissingBraces
{ // violation
    /** @return helper func **/
    boolean condition()
    { // violation
        return false;
    }

    /** Test do/while loops **/
    void testDoWhile()
    { // violation
        // Valid
        do {
            testDoWhile();
        }
        while (condition());

        // Invalid
        do testDoWhile(); while (condition());
    }

    /** Test while loops **/
    void testWhile()
    { // violation
        // Valid
        while (condition()) {
            testWhile();
        }

        // Invalid
        while(condition());
        while (condition())
            testWhile();
        while (condition())
            if (condition())
                testWhile();
    }

    /** Test for loops **/
    void testFor()
    { // violation
        // Valid
        for (int i = 1; i < 5; i++) {
            testFor();
        }

        // Invalid
        for(int i = 1;i < 5;i++);
        for (int i = 1; i < 5; i++)
            testFor();
        for (int i = 1; i < 5;
             i++)
            if (i > 2)
                testFor();
    }

    /** Test if constructs **/
    public void testIf()
    { // violation
        // Valid
        if (condition()) {
            testIf();
        }
        else if (condition()) {
            testIf();
        }
        else {
            testIf();
        }

        // Invalid
        if (condition());
        if (condition())
            testIf();
        if (condition())
            testIf();
        else
            testIf();
        if (condition())
            testIf();
        else {
            testIf();
        }
        if (condition()) {
            testIf();
        }
        else
            testIf();
        if (condition())
            if (condition())
                testIf();
    }

    void whitespaceAfterSemi()
    { // violation
        //reject
        int i = 1;int j = 2;

        //accept
        for (;;) {
        }
    }

    /** Empty constructor block. **/
    public InputLeftCurlyTestMissingBraces() {}

    /** Empty method block. **/
    public void emptyImplementation() {}
}
