/*
JavadocMethod
allowedAnnotations = MyAnnotation
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodsNotSkipWritten {
    /**
     * Description.
     *
     * @param BAD
     *            This param doesn't exist.
     */
    // violation 3 lines above 'Unused @param tag for 'BAD'.'
    @MyAnnotation
    public void InputJavadocMethodsNotSkipWritten() {
    }

    /**
     * Description.
     *
     * @param BAD
     *            This param doesn't exist.
     */
    // violation 3 lines above 'Unused @param tag for 'BAD'.'
    @MyAnnotation
    public void test() {
    }

    /** Description. */
    @MyAnnotation
    public void test2() {
    }

    /** Description. */
    @MyAnnotation
    public String test3(int a) throws Exception {
        return "";
    }
}
@interface MyAnnotation {}
