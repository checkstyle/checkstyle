/*
GenericWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace; // ok

import java.util.List;

public class InputGenericWhitespaceInnerClass<T>
{
    private List<InputGenericWhitespaceInnerClass<? extends T>.InnerClass> field;

    public class InnerClass {
    }
}
