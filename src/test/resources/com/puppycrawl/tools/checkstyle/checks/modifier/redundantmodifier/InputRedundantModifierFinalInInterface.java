/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;


public interface InputRedundantModifierFinalInInterface {
        final int k = 5; // violation

    default int defaultMethod(final int x) {
            if (k == 5) {
                    final int t = 24;  //No violation here!
                    for (; ;) {
                            final String s = "some";  //No violation here!
                    }
            }
        final int square = x * x;  //No violation here!
        return square;
    }
}
