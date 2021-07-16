/*
JavadocMethod
allowedAnnotations = MyAnnotation
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
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
    @MyAnnotation
    public void InputJavadocMethodsNotSkipWritten() { // violation at line 11
    }

    /**
     * Description.
     *
     * @param BAD
     *            This param doesn't exist.
     */
    @MyAnnotation
    public void test() { // violation at line 21
    }

    /** Description. */
    @MyAnnotation
    public void test2() { // ok
    }

    /** Description. */
    @MyAnnotation
    public String test3(int a) throws Exception { // ok
        return "";
    }
}
@interface MyAnnotation {}
