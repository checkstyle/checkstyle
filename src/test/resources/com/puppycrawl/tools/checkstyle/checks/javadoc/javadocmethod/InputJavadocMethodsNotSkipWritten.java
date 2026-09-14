/*
JavadocMethod
allowedAnnotations = MyAnnotation
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
violateExecutionOnNonTightHtml = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodsNotSkipWritten {
    // violation 4 lines below 'Unused @param tag for 'BAD'.'
    /**
     * Description.
     *
     * @param BAD
     *            This param doesn't exist.
     */
    @MyAnnotation
    public void InputJavadocMethodsNotSkipWritten() {
    }

    // violation 4 lines below 'Unused @param tag for 'BAD'.'
    /**
     * Description.
     *
     * @param BAD
     *            This param doesn't exist.
     */
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
