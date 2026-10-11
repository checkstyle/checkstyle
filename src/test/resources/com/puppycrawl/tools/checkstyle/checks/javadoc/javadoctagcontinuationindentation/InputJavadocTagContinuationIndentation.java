/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

import java.io.Serializable;

/**
 * Some javadoc.
 *
 * @since Some javadoc.
 *     Some javadoc.
 * @version 1.0
 * @see Some javadoc.
 *     Some javadoc.
 * @see Some javadoc.
 * @author max
 *     Some javadoc.
 */
class InputJavadocTagContinuationIndentation implements Serializable
{
    /**
     * The client's first name.
     * @serial Some javadoc.
     *     Some javadoc.
     */
    private String fFirstName;

    /**
     * The client's first name.
     * @serial
     *     Some javadoc.
     */
    private String sSecondName;

    /**
     * The client's first name.
     * @serialField
     *     Some javadoc.
     */
    private String tThirdName;

    // violation 8 lines below 'Line continuation .* expected level should be 4'
    /**
     * Some text.
     * @param aString Some javadoc.
     *     Some javadoc.
     * @return Some text.
     * @serialData Some javadoc.
     * @see Some text.
     *    Some javadoc.
     * @throws Exception Some text.
     */
    String method(String aString) throws Exception
    {
        return "null";
    }

    /**
     * Some text.
     * @serialData Some javadoc.
     * @return Some text.
     *     Some javadoc.
     * @param aString Some text.
     * @throws Exception Some text.
     */
    String method1(String aString) throws Exception
    {
        return "null";
    }

    /**
     * Some text.
     * @throws Exception Some text.
     *     Some javadoc.
     * @param aString Some text.
     */
    void method2(String aString) throws Exception {}

    /**
     * Some text.
     * @see Some text.
     * @throws Exception Some text.
     *     Some javadoc.
     */
    void method3() throws Exception {}
}
