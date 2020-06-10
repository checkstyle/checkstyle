package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

class InputNoWhitespaceAfterWhile {
    void method2()
    {
        int temp = -1;
        while(temp < 0) {
            temp++;
            System.out.println("Test if brace on a new line preceeding works.");
        }
    }

    public void while_() {
        int temp1 = -1;
        int temp2 = -1;
        int temp3 = -1;

        while(temp1 < 0) { temp1++; System.out.println("'Should work."); }
        while
            (temp2 < 0) { temp2++; System.out.println("'Should also work."); }
        while (temp3 < 0) { temp3++; System.out.println("This should fail."); }
    }
}
