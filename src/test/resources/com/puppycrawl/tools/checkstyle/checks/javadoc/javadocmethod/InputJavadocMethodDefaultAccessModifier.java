/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = public
allowMissingParamTags = (default)false
allowMissingReturnTag = true
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public interface InputJavadocMethodDefaultAccessModifier {
    /** Javadoc ok here. */
    void testNoViolation(); // ok

    class MyClass {
        /** Missing parameter here. */
        public MyClass(Integer a) { // violation
        }
    }

    /** Missing parameter here, public method by default */
    int testViolationMissingParameter(int b); // violation

    /**
     * Test method.
     *
     * @param c test parameter
     */
    double testNoViolationParameterPresent(int c); // ok
}
