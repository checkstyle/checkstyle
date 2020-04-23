package com.puppycrawl.tools.checkstyle.checks.blocks.emptylinewrappinginblock;

/** Config: default */
public class InputEmptyLineWrappingInBlockDefault {
    public void test() {
        if(true) {
            System.out.println("Test");
        }

        for(int i = 0; i < 10; i++) {
        }

        try {
        } catch (Exception e) {
        }

    }

    static {}
    public InputEmptyLineWrappingInBlockDefault() {}
}

interface Test {
}
