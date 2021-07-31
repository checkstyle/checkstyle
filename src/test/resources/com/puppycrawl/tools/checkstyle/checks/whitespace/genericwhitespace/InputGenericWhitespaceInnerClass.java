/*
GenericWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

import java.util.List;

public class InputGenericWhitespaceInnerClass<T> // ok
{
    private List<InputGenericWhitespaceInnerClass<? extends T>.InnerClass> field;

    public class InnerClass {
    }
}
