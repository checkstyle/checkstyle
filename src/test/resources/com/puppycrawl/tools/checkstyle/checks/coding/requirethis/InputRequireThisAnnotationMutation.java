/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisAnnotationMutation {

    private static final String name = "name";

    @SuppressWarnings(value = name)
    public void foo() {
    }

    @SuppressWarnings(name)
    public void bar() {
    }

    @SuppressWarnings({name})
    public void baz() {
    }
}
