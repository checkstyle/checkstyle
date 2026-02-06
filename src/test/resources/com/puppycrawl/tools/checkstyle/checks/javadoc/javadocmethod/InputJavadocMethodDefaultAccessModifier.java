/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = public
allowMissingParamTags = (default)false
allowMissingReturnTag = true
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public interface InputJavadocMethodDefaultAccessModifier {
    /** Javadoc ok here. */
    void testNoViolation();

    class MyClass {
        /** Missing parameter here. */
        public MyClass(Integer a) { // violation 'Expected @param tag for 'a''
        }
    }

    /** Missing parameter here, public method by default */
    int testViolationMissingParameter(int b); // violation 'Expected @param tag for 'b''

    /**
     * Test method.
     *
     * @param c test parameter
     */
    double testNoViolationParameterPresent(int c);
}
