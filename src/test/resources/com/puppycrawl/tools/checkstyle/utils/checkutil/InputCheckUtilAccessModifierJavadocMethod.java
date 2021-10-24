/*
com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.utils.checkutil;

public class InputCheckUtilAccessModifierJavadocMethod {

    private enum DAY {

        SOME_CONSTANT(1) {
            /** Test Method */
            int someMethod() { // ok
                return 0;
            }
        };

        /** Test constructor */
        DAY(int i) { // violation
        }
    }
}
