/*
RedundantModifier
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE
jdkVersion = (default)22


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
            @Override
            public final void innerTest() { // violation
            }
        };
    }
}
