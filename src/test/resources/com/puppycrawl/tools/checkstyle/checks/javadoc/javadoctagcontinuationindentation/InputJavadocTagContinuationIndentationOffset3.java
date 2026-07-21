/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = 3


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

    // violation 5 lines below 'Line continuation .* expected level should be 3'
    /**
     * Some javadoc.
     *
     * @since Some javadoc.
     *   Some javadoc.
 * @version 1.0
 * @deprecated Some javadoc.
 *    Some javadoc.
 * @see Some javadoc.
 * @author max
 *     Some javadoc.
 */
class InputJavadocTagContinuationIndentationOffset3 {
    // violation 4 lines below 'Line continuation .* expected level should be 3'
        /**
     * The client's first name.
     * @serial Some javadoc.
        *   Some javadoc.
     */
    private String fFirstName;

    /**
     * The client's first name.
     * @serial
     *    Some javadoc.
     */
    private String sSecondName;

    /**
     * The client's first name.
     * @serialField
     *     Some javadoc.
     */
    private String tThirdName;
}
