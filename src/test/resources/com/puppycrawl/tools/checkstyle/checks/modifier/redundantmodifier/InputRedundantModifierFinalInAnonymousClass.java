/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public class InputRedundantModifierFinalInAnonymousClass {
    public static abstract class Example {
        public abstract void innerTest();

        public final void test() {
        }
    }

    public static void test() {
        new Example() {
            // violation 2 lines below 'Redundant 'final' modifier.'
            @Override
            public final void innerTest() {
            }
        };
    }
}
