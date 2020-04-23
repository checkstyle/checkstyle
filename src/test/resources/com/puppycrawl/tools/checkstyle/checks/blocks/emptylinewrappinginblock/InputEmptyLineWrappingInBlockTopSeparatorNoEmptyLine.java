package com.puppycrawl.tools.checkstyle.checks.blocks.emptylinewrappinginblock;

/**
 * Config:
 *  - topSeparator = no_empty_line
 *  - tokens = ALL_TOKENS
 */
public class InputEmptyLineWrappingInBlockTopSeparatorNoEmptyLine { // violation

    public InputEmptyLineWrappingInBlockTopSeparatorNoEmptyLine() {} // violation
    public void test() {}

    public void test2() {} // violation
    public void test3() {
        int x = 10;
        if(x == 1) {
            // Do something
        }
        else {
            // Do something else
        }
        try {
            throw new Exception();
        } catch (Exception e) {
            // Handle exception
        }

    }

    public void test4() { // violation
        int x = 10;

        if(x == 1) { // violation
            // Do something
        }

        else { // violation
            // Do something else
        }

        try { // violation
            throw new Exception();
        } catch (Exception e) {
            // Handle Exception
        }
    }
}

interface TestInterfaceEmptyLine { // violation
}
