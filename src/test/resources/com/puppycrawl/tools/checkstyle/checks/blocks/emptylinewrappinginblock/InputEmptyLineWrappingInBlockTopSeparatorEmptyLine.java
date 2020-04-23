package com.puppycrawl.tools.checkstyle.checks.blocks.emptylinewrappinginblock;

/**
 * Config:
 *  - topSeparator = empty_line
 *  - tokens = ALL TOKENS
 */
public class InputEmptyLineWrappingInBlockTopSeparatorEmptyLine {
    public InputEmptyLineWrappingInBlockTopSeparatorEmptyLine() {} // violation

    public void test() {}
    public void test2() {} // violation

    public void test3() {
        int x = 10;

        int arr[] = new int[] {
          1, 2
        };

        if(x == 1) {
            // Do something
        }

        // This is a comment before a block.
        else {
            // Do something else
        }

        // These are multiple
        // inline comments before a block.
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

    public void testOthers() {
        int key = 1;
        switch (key) { // violation
            case 1: { // violation
                break;
            }

            case 2:
                break;

            default:
                break;
        }

        String s = null;

        if(s == null) {
            s = "";
        } else if(s.length() == 0) { // violation
            s = "foobar";
        }
    }

    public void testScopeBlock() {
        int y;
        { // violation
            int x = 10;
        }
    }
}
interface TestInterface { // violation
}
