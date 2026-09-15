/*
EmptyLineWrappingInBlock
tokens = CLASS_DEF, METHOD_DEF
topSeparator = empty_line_allowed
bottomSeparator = empty_line_allowed

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public class InputEmptyLineWrappingInBlockAllowed {
    private int a;
    public void m() {
        int x = 1;
    }
}
