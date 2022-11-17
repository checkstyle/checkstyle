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
        // empty lines below


    }

    private int counter;

    private Object obj = null;

    abstract int generateSrc(String s);

    static {
        // empty lines below


    }

    {
        // empty lines below


    }

    private static void foo() {


        // 1 empty line above

        // 1 empty line above



        // 2 empty lines above
    }
}
class // violation ''CLASS_DEF' should be separated from previous line.'
SecondClassReturnWithVeryVeryVeryLongName3{}
