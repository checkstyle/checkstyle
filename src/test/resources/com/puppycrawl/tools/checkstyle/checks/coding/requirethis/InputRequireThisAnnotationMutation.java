/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisAnnotationMutation {

    private String value;
    private String name;
    private String outer;

    @SuppressWarnings(value = "unused")
    public void foo() {
    }

    @MyAnnotation(name = "someName")
    public void bar() {
    }

    @DeeplyNested(
        outer = @MyAnnotation(
            name = "inner"
        )
    )
    public void baz() {
    }

    public void setValues(String value, String name, String outer) {
        value = value; // violation 'Reference to instance variable 'value' needs "this.".'
        name = name;   // violation 'Reference to instance variable 'name' needs "this.".'
        outer = outer; // violation 'Reference to instance variable 'outer' needs "this.".'
    }
}

@interface MyAnnotation {
    String name();
}

@interface DeeplyNested {
    MyAnnotation outer();
}
