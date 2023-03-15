/*
ParameterAssignment


*/

package com.puppycrawl.tools.checkstyle.checks.coding.parameterassignment;

import javax.annotation.Nullable;

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

    SomeInterface obj = q -> q++; // violation
    SomeInterface obj2 = (int q) -> q += 12; // violation
    SomeInterface obj3 = (w) -> w--; // violation
    AnotherInterface obj4 = (int q, int w) -> obj.equals(obj2);
    AnotherInterface obj5 = (q, w) -> w = 14; // violation
    SomeInterface obj6 = (@Nullable int a) -> a += 12; // violation
    AnotherInterface obj7 = (@Nullable int c, @Nullable int d) -> {
        c += d; // violation
        d += c; // violation
    };

    void method() {
        int q = 12;
        SomeInterface obj = (d) -> {
            SomeInterface b = (c) -> obj2.equals(obj4);
            int c = 12;
            c++;
            SomeInterface r = (field) -> this.field++;
            d -= 10; // violation
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
