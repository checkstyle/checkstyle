/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4
forceStrictCondition = true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

public class InputJavadocTagContinuationIndentationForceStrictCondition {

    /**
     * The client's first name.
     * @serial Some javadoc.
     *      Some javadoc.
     */
    private String fFirstName;
    // violation 3 lines above 'Line continuation .* expected level should be 4'

    /**
     * Desc.
     *
     * @param s
     *     Desc.
     *     Desc 2.
     *     Desc 3.
     *                         Desc 4. violation is expected
     */
    public void test(String s) {
        // violation 3 lines above 'Line continuation .* expected level should be 4'
    }


    /**
     * Some description here.
     *
     * @param a Some description here.
     * Line with indent 0.
     *   Line with indent 2.
     *     Line with indent 4.
     *         Line with indent 9.
     */
    public void test(int a) {
        // violation 6 lines above 'Line continuation .* expected level should be 4'
        // violation 6 lines above 'Line continuation .* expected level should be 4'
        // violation 5 lines above 'Line continuation .* expected level should be 4'
    }
}
