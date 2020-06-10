package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

class InputNoWhitespaceAfterFor {
    void method2()
    {
        for(int temp = -1; temp < 0; temp++) {
            temp++;
            System.out.println("Test if brace on a new line preceeding works.");
        }
    }

    public void while_() {
        for(int temp = -1; temp < 0; temp++) {
            System.out.println("'Should work.");
        }
        for
            (int temp = -1; temp < 0; temp++) {
            System.out.println("'Should also work.");
        }
        for (int temp = -1; temp < 0; temp++) {
            System.out.println("This should fail.");
        }
    }
}
