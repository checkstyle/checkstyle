/*
EmptyLineWrappingInBlock
tokens = CLASS_DEF, METHOD_DEF, CTOR_DEF, RECORD_DEF, COMPACT_CTOR_DEF
topSeparator = empty_line_allowed
bottomSeparator = empty_line_allowed

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public class InputEmptyLineWrappingInBlockAllowed {
    private int a;
    public void m() {
        int x = 1;
    }
    InputEmptyLineWrappingInBlockAllowed() {

        int a = 0;

    }
    record Point(int x, int y) {

        Point {

            int a = 0;

        }

    }

}
