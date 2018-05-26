package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

import java.util.Comparator;

public class InputHiddenFieldStaticVisibility {
    static int someField;
    static Object other = null;
    Object field = null;

    static void method(Object field, Object other) {
        // field 'field' can not be referenced form a static context
        // static field 'other' can be referenced from a static context
    }

    static class B {
        void method(Object field, Object other) {
            // field 'field' can not be referenced form a static context
            // static field 'other' can be referenced from a static context
        }
    }

    static Comparator<Object> COMP = new Comparator<Object>() {
        @Override
        public int compare(Object field, Object other) {
            // field 'field' can not be referenced form a static context
            // static field 'other' can be referenced from a static context
            return 0;
        }
    };

    static Comparator<Object> createComp() {
        return new Comparator<Object>() {
            @Override
            public int compare(Object field, Object other) {
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
