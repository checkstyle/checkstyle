/*
EmptyLineWrappingInBlock
tokens = METHOD_DEF
topSeparator = empty_line_allowed
bottomSeparator = no_empty_line


*/

package org.checkstyle.suppressionxpathfilter.whitespace.emptylinewrappinginblock;

public class InputXpathEmptyLineWrappingInBlockThree {
    public void bar() {
        int a = 1;

    } // violation "'}' can not have empty line before."
}
