/*
ParameterAssignment


*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameterassignment;

public class InputParameterAssignmentWithUnchecked {
    int field;
    void foo1(int field) {
        int i = field;
        this.field = field;
        i++;
        field = 0; // violation
        field += 1; // violation
        this.field++;
        field--; // violation
    }
    // without parameters
    void foo2() {
        field = 0;
    }
    @SuppressWarnings(value = "unchecked")
    void foo3(String field, int field1) {
        this.field = (field1 += field.length()); // violation
    }

    void foo4() {
        String hidden = "";
        new NestedClass() {
            public void test(String hidden) {
            }
        };
        hidden += "test";
    }

    // parameter name must be the same token name
    void foo5(int EXPR) {
        int i = EXPR;
    }

    public static abstract class NestedClass {
        public abstract void test(String hidden);
    }
}
