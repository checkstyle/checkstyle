/*
com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = public
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.utils.checkutil;

public class InputCheckUtil7 {

    static public class TestException3 extends Exception {
        TestException3(String messg) {
            super(messg);
        }

        /**
         *
         */
        static public void method(int i) { // violation 'Expected @param tag for 'i''

        }
    }
}
