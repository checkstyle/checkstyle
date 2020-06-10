package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

class InputNoWhitespaceAfterCatch {
    void method2()
    {
        try {
            System.out.println("Test if brace on a new line preceeding works.");
        } catch(final Exception e) {}
    }

    public void catch_() {
        try {
            System.out.println("Standard; this should work.");
        } catch(final Exception e) {}
        try {
            System.out.println("New lines should also work.");
        } catch
            (final Exception e) {}
        try {
            System.out.println("Space: this should fail.");
        } catch (final Exception e) {}
    }
}
