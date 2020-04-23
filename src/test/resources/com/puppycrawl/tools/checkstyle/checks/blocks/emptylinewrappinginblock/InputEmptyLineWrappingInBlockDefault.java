package com.puppycrawl.tools.checkstyle.checks.blocks.emptylinewrappinginblock;

/** Config: default */
public class InputEmptyLineWrappingInBlockDefault {
    public void test() { // violation
        if(true) { // violation
            System.out.println("Test");
        }

        for(int i = 0; i < 10; i++) {
        }

        try {
        } catch (Exception e) {
        }

    }

    static {} // violation
    public InputEmptyLineWrappingInBlockDefault() {} // violation
}

interface Test {
}

