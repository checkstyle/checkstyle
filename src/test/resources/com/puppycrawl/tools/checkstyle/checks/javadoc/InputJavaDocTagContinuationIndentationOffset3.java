package com.puppycrawl.tools.checkstyle.checks.javadoc;

/**
 * Some javadoc.
 *
 * @since Some javadoc.
 *   Some javadoc. // warn
 * @version 1.0
 * @deprecated Some javadoc.
 *    Some javadoc.
 * @see Some javadoc.
 * @author max
 *     Some javadoc.
 */
class JavaDocTagContinuationIndentationOffset3 {
	/**
     * The client's first name.
     * @serial Some javadoc.
	*   Some javadoc. // warn
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
