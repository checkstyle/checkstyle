/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA


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
        // violation below 'Redundant 'final' modifier.'
        public static final void someStaticMethod() { }
        public static void someMethod() { }
    }
}

