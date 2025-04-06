/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public enum InputRedundantModifierFinalInEnumMethods {
    E1,
    E2 {
        @Override
        public final void v() { // violation
        }
    };

    public void v() {
    }

    // not redundant since field can override this method if 'final' is removed
    // and that may not be desirable
    public final void v2() {
    }
}
enum InputRedundantModifierFinalInEnumMethods2 {
    E1 {
        @Override
        public final void v() { // violation
        }
    },
    E2 {
        @Override
        public void v() {
        }
    };

    public abstract void v();
}
