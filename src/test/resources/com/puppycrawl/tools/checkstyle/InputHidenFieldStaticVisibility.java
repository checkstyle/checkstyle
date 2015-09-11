package com.puppycrawl.tools.checkstyle;

import java.util.Comparator;

public class InputHidenFieldStaticVisibility {
    static int someField;
    static InputHidenFieldStaticVisibility other = null;
    InputHidenFieldStaticVisibility field = null;

    static void method(InputHidenFieldStaticVisibility field, InputHidenFieldStaticVisibility other) {
        // field 'field' can not be referenced form a static context
        // static field 'other' can be referenced from a static context
    }

    static class B {
        void method(InputHidenFieldStaticVisibility field, InputHidenFieldStaticVisibility other) {
            // field 'field' can not be referenced form a static context
            // static field 'other' can be referenced from a static context
        }
    }

    static Comparator<InputHidenFieldStaticVisibility> COMP = new Comparator<InputHidenFieldStaticVisibility>() {
        @Override
        public int compare(InputHidenFieldStaticVisibility field, InputHidenFieldStaticVisibility other) {
            // field 'field' can not be referenced form a static context
            // static field 'other' can be referenced from a static context
            return 0;
        }
    };

    static Comparator<InputHidenFieldStaticVisibility> createComp() {
        return new Comparator<InputHidenFieldStaticVisibility>() {
            @Override
            public int compare(InputHidenFieldStaticVisibility field, InputHidenFieldStaticVisibility other) {
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
