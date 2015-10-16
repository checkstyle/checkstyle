package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.List;

public class InputGenericWhitespaceInnerClassCheck<T>
{
    private List<InputGenericWhitespaceInnerClassCheck<? extends T>.InnerClass> field;

    public class InnerClass {
    }
}
