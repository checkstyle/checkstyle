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

public class InputJavadocMethod_01 {

    /** Do 1.
     * @throws TestException1 when problem occurs.
     */
    public void doStuff1() throws TestException1 { // ok
        try {
            doStuff2(); // ok
        } catch (final TestException2 e) { }
        throw new InputJavadocMethod_01().new TestException1(""); // ok
    }
    /** Do 2.
     * @throws TestException2 when problem occurs.
     */
    private static void doStuff2() throws TestException2 { // ok
        throw new TestException2(""); // ok
    }
    /** Exception 1.
     */
    class TestException1 extends Exception { // ok
        /** Exception 1.
         * @param messg message
         */
        TestException1(String messg) { // ok
            super(messg);
        }
    }
    /** Exception 2.
     */
    public static class TestException2 extends Exception { // ok
        /** Exception 2.
         * @param messg message
         */
        TestException2(String messg) { // ok
            super(messg);
        }
    }
}
