/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethod_1379666 {
    /**
     * @throws BadStringFormat some text
     */
    public void ok() throws BadStringFormat { // ok
    }


    /**
     * Some comment.
     * @throws java.lang.Exception some text
     */
    public void error1() // ok
        throws java.lang.Exception {
    }

    /**
     * @throws InputJavadocMethod_1379666.BadStringFormat some text
     */
    public void error2() throws InputJavadocMethod_1379666.BadStringFormat { // ok
    }

    /**
     * Some exception class.
     */
    public static class BadStringFormat extends Exception { // ok
        /**
         * Some comment.
         * @param s string.
         */
        BadStringFormat(String s) { // ok
            super(s);
        }
    }
}
