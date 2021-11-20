/*
OperatorWrap
option = (default)nl
tokens = ASSIGN


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

import java.lang.annotation.Repeatable;
import java.util.Arrays;

@Annotation1(example = { // ok
    "foo",
    "bar"
})
@Annotation2(example = // violation
{
    "foo",
    "bar"
})
public class InputOperatorWrapArrayAssign {
    public String[] array = { // ok
        "foo",
        "bar"
    };
    public String[] array2 = // violation
    {
        "foo",
        "bar"
    };
}

@interface Annotation1 {
    String[] example();
}
@interface Annotation2 {
    String[] example();
}
