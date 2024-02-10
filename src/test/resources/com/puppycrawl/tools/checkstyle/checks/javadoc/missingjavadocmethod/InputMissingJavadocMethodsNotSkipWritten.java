/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = MyAnnotation
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodsNotSkipWritten {
    /**
     * Description.
     *
     * @param BAD
     *            This param doesn't exist.
     */
    @MyAnnotation
    public void InputMissingJavadocMethodsNotSkipWritten() {
    }

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
