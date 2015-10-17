package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Comparator;

public class InputHiddenFieldStaticVisibility {
    static int someField;
    static InputHiddenFieldStaticVisibility other = null;
    InputHiddenFieldStaticVisibility field = null;

    static void method(InputHiddenFieldStaticVisibility field, InputHiddenFieldStaticVisibility other) {
        // field 'field' can not be referenced form a static context
        // static field 'other' can be referenced from a static context
    }

    static class B {
        void method(InputHiddenFieldStaticVisibility field, InputHiddenFieldStaticVisibility other) {
            // field 'field' can not be referenced form a static context
            // static field 'other' can be referenced from a static context
        }
    }

    static Comparator<InputHiddenFieldStaticVisibility> COMP = new Comparator<InputHiddenFieldStaticVisibility>() {
        @Override
        public int compare(InputHiddenFieldStaticVisibility field, InputHiddenFieldStaticVisibility other) {
            // field 'field' can not be referenced form a static context
            // static field 'other' can be referenced from a static context
            return 0;
        }
    };

    static Comparator<InputHiddenFieldStaticVisibility> createComp() {
        return new Comparator<InputHiddenFieldStaticVisibility>() {
            @Override
            public int compare(InputHiddenFieldStaticVisibility field, InputHiddenFieldStaticVisibility other) {
                // field 'field' can not be referenced form a static context
                // static field 'other' can be referenced from a static context
                return 0;
            }
        };
    }

    static void foo1(int a) {}

    void foo2(int a) {}

    static void foo3(int someField) {}
}
