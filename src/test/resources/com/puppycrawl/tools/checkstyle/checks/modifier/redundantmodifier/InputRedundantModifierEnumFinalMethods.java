/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public class InputRedundantModifierEnumFinalMethods {

    enum ImplicitlyFinal {
        A, B;

        final int code() { // violation 'Redundant 'final' modifier.'
            return ordinal();
        }

        int plain() {
            return 1;
        }
    }

    enum NotFinal {
        A {
            @Override
            int code() {
                return 1;
            }
        },
        B;

        int code() {
            return 0;
        }

        final int fixed() {
            return 2;
        }
    }

    enum Empty {
        ;

        final void method() { // violation 'Redundant 'final' modifier.'
        }
    }

    enum WithStatic {
        A;

        static final void util() { // violation 'Redundant 'final' modifier.'
        }
    }
}
