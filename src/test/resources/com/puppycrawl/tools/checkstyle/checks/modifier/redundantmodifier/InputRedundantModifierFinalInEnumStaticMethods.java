/*
RedundantModifier
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE
jdkVersion = (default)22


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public class InputRedundantModifierFinalInEnumStaticMethods {
    public enum TestEnum {
        VALUE {
           @Override public void someMethodToOverride() {
                // do it differently
            }
         };

        public void someMethodToOverride() { }
        public static final void someStaticMethod() { }    // violation
        public static void someMethod() { }
    }
}

