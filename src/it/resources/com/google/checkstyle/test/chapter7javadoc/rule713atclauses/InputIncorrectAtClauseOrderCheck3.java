package com.google.checkstyle.test.chapter7javadoc.rule713atclauses;

import java.io.Serializable;

/**
 * Some javadoc.
 *
 * @since Some javadoc.
 * @version 1.0
 * @deprecated Some javadoc.
 * @see Some javadoc.
 * @author max
 */
class InputIncorrectAtClauseOrderCheck3 implements Serializable
{

    /**
     * Some text.
     * @deprecated Some text.
     * @return Some text. //warn
     * @param aString Some text. //warn
     */
    String method5(String aString)
    {
        return "null";
    }

    /**
     * Some text.
     * @param aString Some text.
     * @return Some text.
     * @serialData Some javadoc.
     * @param aInt Some text. //warn
     * @throws Exception Some text.
     * @param aBoolean Some text. //warn
     * @deprecated Some text.
     */
    String method6(String aString, int aInt, boolean aBoolean) throws Exception
    {
        return "null";
    }

    /**
     * Some javadoc.
     *
     * @version 1.0
     * @since Some javadoc.
     * @serialData Some javadoc.
     * @author max
     */
    class InnerClassWithAnnotations3
    {

        /**
         * Some text.
         * @param aString Some text.
         * @deprecated Some text.
         * @return Some text. //warn
         */
        String method5(String aString)
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @param aInt Some text. //warn
         * @throws Exception Some text.
         * @param aBoolean Some text. //warn
         * @deprecated Some text.
         */
        String method6(String aString, int aInt, boolean aBoolean) throws Exception
        {
            return "null";
        }
    }

    InnerClassWithAnnotations3 anon = new InnerClassWithAnnotations3()
    {

        /**
         * Some text.
         * @deprecated Some text.
         * @return Some text. //warn
         * @param aString Some text. //warn
         */
        String method5(String aString)
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @param aInt Some text. //warn
         * @throws Exception Some text.
         * @param aBoolean Some text. //warn
         * @deprecated Some text.
         */
        String method6(String aString, int aInt, boolean aBoolean) throws Exception
        {
            return "null";
        }
    };
}
