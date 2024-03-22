package com.google.checkstyle.test.chapter7javadoc.rule713atclauses;

import java.io.Serializable;

/**
 * Some javadoc.
 *
 * @author max
 * @version 1.0
 * @see Some javadoc.
 * @since Some javadoc.
 * @deprecated Some javadoc.
 */
class InputCorrectAtClauseOrderCheck1 implements Serializable
{

    /**
     * The client's first name.
     * @serial
     */
    private String fFirstName;

    /**
     * The client's first name.
     * @serial
     */
    private String sSecondName;

    /**
     * The client's first name.
     * @serialField
     */
    private String tThirdName;

    /**
     * Some text.
     * @param aString Some text.
     * @return Some text.
     * @throws Exception Some text.
     * @serialData Some javadoc.
     * @deprecated Some text.
     */
    String method(String aString) throws Exception {
        return "null";
    }

    /**
     * Some text.
     * @param aString Some text.
     * @return Some text.
     * @throws Exception Some text.
     * @serialData Some javadoc.
     */
    String method1(String aString) throws Exception {
        return "null";
    }

    /**
     *
     * @author max
     * @version 1.0
     * @since Some javadoc.
     */
    class InnerClassWithAnnotations1 {
        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @throws Exception Some text.
         * @deprecated Some text.
         */
        String method(String aString) throws Exception {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @throws Exception Some text.
         * @serialData Some javadoc.
         */
        String method1(String aString) throws Exception {
            return "null";
        }
    }

    InnerClassWithAnnotations1 anon = new InnerClassWithAnnotations1()
    {
        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @throws Exception Some text.
         * @deprecated Some text.
         */
        String method(String aString) throws Exception {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @throws Exception Some text.
         */
        String method1(String aString) throws Exception {
            return "null";
        }
    };
}
