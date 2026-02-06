/*
JavadocMethod
validateThrows = true

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodEdgeCases {

    /** Short javadoc on same line. */
    public void methodWithShortJavadoc() {
    }

    /**
     * Javadoc with exact column position.
     * @throws Exception never
     */
    public void methodWithException() throws Exception {
    }

    /** Inline javadoc. @param x value */
    // violation below 'Expected @param tag for 'x''
    public void methodWithParam(int x) {
    }

    /**
     * Multi-line javadoc
     * with several lines
     * @return value
     */
    public int methodWithReturn() {
        return 0;
    }

    /**
     * Javadoc before annotation.
     * @param val the value
     */
    @SuppressWarnings("unused")
    public void methodWithAnnotation(String val) {
    }

    /**
     * Non-javadoc comment should be ignored.
     */
    @Deprecated
    public void methodWithDeprecated() {
    }

    /* Not a javadoc comment */
    public void methodWithRegularComment() {
    }

    /**
     * Method with missing throws tag.
     */
    // violation below 'Expected @throws tag for 'IllegalArgumentException''
    public void methodMissingThrows() throws IllegalArgumentException {
    }

    /**
     * Javadoc with multiple annotations.
     * @param x value
     */
    @Deprecated
    @SuppressWarnings("unused")
    public void methodWithMultipleAnnotations(int x) {
    }

        /** Javadoc indented with spaces at column 8. */
        public void methodIndented() {
        }
}
