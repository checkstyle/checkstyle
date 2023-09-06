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
@Annotation2(example = // violation ''=' should be on a new line.'
{
    "foo",
    "bar"
})
@Nested(
    nestedAnn = { // ok
        @Annotation1(example = { // ok
            "foo",
            "bar"
        })
    }
)
public class InputOperatorWrapArrayAssign {
    public String[] array = { // ok
        "foo",
        "bar"
    };
    public String[] array2 = // violation ''=' should be on a new line.'
    {
        "foo",
        "bar"
    };
}

@Annotation1(
    value = "1",
    example = { // ok
        "foo",
        "bar"
    },
    other = "2"
)
class Second {}

@Annotation1(
    example = { // ok
        "foo",
        "bar"
    },
    arr = { // ok
        "1"
    }
)
class Third {}

@interface Annotation1 {
    String[] example();
    String value() default "";
    String other() default "";
    String[] arr() default "";
}

@interface Annotation2 {
    String[] example();
}

@interface Nested {
    Annotation1[] nestedAnn();
}
