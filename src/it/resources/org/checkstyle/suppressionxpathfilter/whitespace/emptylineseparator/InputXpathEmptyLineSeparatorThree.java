/*
EmptyLineSeparator
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
allowNoEmptyLineBetweenFields = (default)false
tokens = METHOD_DEF


*/

///////////////////////////////////////////////////
//HEADER
///////////////////////////////////////////////////
package org.checkstyle.suppressionxpathfilter.whitespace.emptylineseparator;
import java.io.*;
class InputXpathEmptyLineSeparatorThree {
    public static final int FOO_CONST = 1;
    public void foo() {}
    public void foo1() {} // violation "'METHOD_DEF' should be separated from previous line."
}
