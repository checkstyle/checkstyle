/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = false
tokens = CLASS_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public abstract class InputEmptyLineSeparatorMultipleEmptyLinesInside3
{
    public InputEmptyLineSeparatorMultipleEmptyLinesInside3() {
        // empty lines below should cause a violation


    }

    private int counter;

    private Object obj = null;

    abstract int generateSrc(String s);

    static {
        // empty lines below should cause a violation


    }

    {
        // empty lines below should cause a violation


    }

    private static void foo() {


        // 1 empty line above should cause a violation

        // 1 empty line above should not cause a violation



        // 2 empty lines above should cause violations
    }
}
class SecondClassReturnWithVeryVeryVeryLongName3{} // violation
