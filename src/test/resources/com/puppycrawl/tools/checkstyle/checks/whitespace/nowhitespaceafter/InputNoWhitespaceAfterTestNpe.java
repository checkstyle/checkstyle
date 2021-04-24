package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

/*
 * Config: default
 */
public class InputNoWhitespaceAfterTestNpe
{
    private int[] getSome() {
        return new int[4]; // ok
    }
}
