/*
ParameterAssignment


*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameterassignment;

import jakarta.annotation.Nullable;

public class InputParameterAssignmentWithUnchecked {
    int field;
    void foo1(int field) {
        int i = field;
        this.field = field;
        i++;
        field = 0; // violation 'Assignment of parameter 'field' is not allowed.'
        field += 1; // violation 'Assignment of parameter 'field' is not allowed.'
        this.field++;
        field--; // violation 'Assignment of parameter 'field' is not allowed.'
    }
    // without parameters
    void foo2() {
        field = 0;
    }
    @SuppressWarnings(value = "unchecked")
    void foo3(String field, int field1) {
        // violation below 'Assignment of parameter 'field1' is not allowed.'
        this.field = (field1 += field.length());
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

    SomeInterface obj = q -> q++; // violation 'Assignment of parameter 'q' is not allowed.'

    // violation below 'Assignment of parameter 'q' is not allowed.'
    SomeInterface obj2 = (int q) -> q += 12;

    SomeInterface obj3 = (w) -> w--; // violation 'Assignment of parameter 'w' is not allowed.'

    AnotherInterface obj4 = (int q, int w) -> obj.equals(obj2);

    // violation below 'Assignment of parameter 'w' is not allowed.'
    AnotherInterface obj5 = (q, w) -> w = 14;

    // violation below 'Assignment of parameter 'a' is not allowed.'
    SomeInterface obj6 = (@Nullable int a) -> a += 12;

    AnotherInterface obj7 = (@Nullable int c, @Nullable int d) -> {
        c += d; // violation 'Assignment of parameter 'c' is not allowed.'
        d += c; // violation 'Assignment of parameter 'd' is not allowed.'
    };

    void method() {
        int q = 12;
        SomeInterface obj = (d) -> {
            SomeInterface b = (c) -> obj2.equals(obj4);
            int c = 12;
            c++;
            SomeInterface r = (field) -> this.field++;
            d -= 10; // violation 'Assignment of parameter 'd' is not allowed.'
        };
    }

    public static abstract class NestedClass {
        public abstract void test(String hidden);
    }

    public interface SomeInterface {
        void method(int a);
    }

    public interface AnotherInterface {
        void method(int a, int b);
    }
}
