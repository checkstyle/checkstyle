/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;


public interface InputRedundantModifierFinalInInterface {
        final int k = 5; // violation 'Redundant 'final' modifier.'

    default int defaultMethod(final int x) {
            if (k == 5) {
                    final int t = 24;
                    for (; ;) {
                            final String s = "some";
                    }
            }
        final int square = x * x;
        return square;
    }
}
