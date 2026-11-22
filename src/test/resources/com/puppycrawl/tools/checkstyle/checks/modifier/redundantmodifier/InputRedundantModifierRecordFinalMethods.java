/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public class InputRedundantModifierRecordFinalMethods {

    record Point(int x, int y) {
        final int sum() { // violation 'Redundant 'final' modifier.'
            return x + y;
        }

        @SafeVarargs
        final void varargs(java.util.List<String>... lists) {
        }

        static final int MAX = 1;

        int plain() {
            return x;
        }
    }

    void method() {
        record Local(int value) {
            final int doubled() { // violation 'Redundant 'final' modifier.'
                return value * 2;
            }
        }
    }

    class NotFinal {
        final void allowed() {
        }

        record Nested(int value) {
            final int value2() { // violation 'Redundant 'final' modifier.'
                return value;
            }
        }
    }
}
