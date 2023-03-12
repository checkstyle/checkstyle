/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/
//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisValidateOnlyOverlappingFalseLeaves {
    InputRequireThisValidateOnlyOverlappingFalseLeaves() {}
    class NestedClass {
        NestedClass() {}
    }
    interface NestedInterface {}
    enum NestedEnum {}
    @interface NestedAnnotation {}
    record NestedRecord() {}

    private int id(int i) {
        return i;
    }

    private final int field = id(0); // violation 'Method call to 'id' needs "this.".'
}
class Method {
    private boolean _a = false;

    public boolean equals(Object object) {
        new NestedClass() {
            public void method() {}
            public void method2() {}
        };
        return _a; // violation 'Reference to instance variable '_a' needs "this.".'
    }
}
class NestedClass {}
