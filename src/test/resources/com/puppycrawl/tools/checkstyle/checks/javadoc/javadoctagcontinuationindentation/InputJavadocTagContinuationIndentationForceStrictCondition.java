package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

/**
 * Config:
 *  - forceStrictCondition = true
 */
class InputJavadocTagContinuationIndentationForceStrictCondition {
        /**
     * The client's first name.
     * @serial Some javadoc.
        *      Some javadoc. // violation
     */
    private String fFirstName;

    /**
     * Some description here.
     *
     * @param a Some description here.
     * Line with indent 0. // violation
     *   Line with indent 2. // violation
     *     Line with indent 4. // OK
     *         Line with indent 9. // violation
     */
    public void test(int a) {}
}
