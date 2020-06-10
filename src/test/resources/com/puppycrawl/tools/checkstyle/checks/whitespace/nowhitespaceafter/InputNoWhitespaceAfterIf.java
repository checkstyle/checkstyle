package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

class InputNoWhitespaceAfterIf {
    void method2()
    {
        if(true) {
            System.out.println("Test if brace on a new line preceeding works.");
        }
    }

    public void if_() {
        if(false) { System.out.println("Standard; should work."); }
        if
            (true) { System.out.println("New lines should also work."); }
        if (false) { System.out.println("Space: this should fail."); }
    }
}
