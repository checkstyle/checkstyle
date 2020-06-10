package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

class InputNoWhitespaceAfterDoWhile {
    void method2()
    {
        do {
            System.out.println("Test if brace on a new line preceeding works.");
        } while(false);
    }

    public void catch_() {
        do {
            System.out.println("Standard; this should work.");
        } while(false);
        do {
            System.out.println("New lines should also work.");
        } while
            (false);
        do {
            System.out.println("Space: this should fail.");
        } while (false);
    }
}
