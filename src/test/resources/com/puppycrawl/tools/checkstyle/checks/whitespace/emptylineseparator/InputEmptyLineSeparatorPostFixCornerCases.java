package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

/**
 * Config:
 * allowMultipleEmptyLinesInsideClassMembers = false
 */
public class InputEmptyLineSeparatorPostFixCornerCases {

    protected void foo1() throws Exception {
        int a = 25;
        // violation


        foo(a);
    }

    void foo(int a) {
        System.out.println(a);
    }

    protected void foo() {

        Object[] defaults = new Object[] {
                "String One",
                // violation


                "String Two",

        };
    }
}
