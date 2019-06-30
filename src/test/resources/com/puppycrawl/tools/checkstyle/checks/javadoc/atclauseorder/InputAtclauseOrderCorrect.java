package com.puppycrawl.tools.checkstyle.checks.javadoc.atclauseorder;

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
class InputAtclauseOrderCorrect implements Serializable
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
    String method(String aString) throws Exception
    {
        return "null";
    }

    /**
     * Some text.
     * @param aString Some text.
     * @return Some text.
     * @throws Exception Some text.
     * @serialData Some javadoc.
     */
    String method1(String aString) throws Exception
    {
        return "null";
    }

    /**
     * Some text.
     * @param aString Some text.
     * @throws Exception Some text.
     */
    void method2(String aString) throws Exception {}

    /**
     * Some text.
     * @throws Exception Some text.
     * @deprecated Some text.
     */
    void method3() throws Exception {}

    /**
     * Some text.
     * @return Some text.
     * @throws Exception Some text.
     */
    String method4() throws Exception
    {
        return "null";
    }

    /**
     * Some text.
     * @param aString Some text.
     * @return Some text.
     * @serialData Some javadoc.
     * @deprecated Some text.
     */
    String method5(String aString)
    {
        return "null";
    }

    /**
     * Some text.
     * @param aString Some text.
     * @param aInt Some text.
     * @param aBoolean Some text.
     * @return Some text.
     * @throws Exception Some text.
     * @deprecated Some text.
     */
    String method6(String aString, int aInt, boolean aBoolean) throws Exception
    {
        return "null";
    }

    /**
     *
     * @author max
     * @version 1.0
     * @since Some javadoc.
     */
    class InnerClassWithAnnotations
    {
        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @throws Exception Some text.
         * @deprecated Some text.
         */
        String method(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @throws Exception Some text.
         * @serialData Some javadoc.
         */
        String method1(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @throws Exception Some text.
         */
        void method2(String aString) throws Exception {}

        /**
         * Some text.
         * @throws Exception Some text.
         * @deprecated Some text.
         */
        void method3() throws Exception {}

        /**
         * Some text.
         * @return Some text.
         * @throws Exception Some text.
         * @serialData Some javadoc.
         */
        String method4() throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @serialData Some javadoc.
         * @deprecated Some text.
         */
        String method5(String aString)
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @param aInt Some text.
         * @param aBoolean Some text.
         * @return Some text.
         * @throws Exception Some text.
         * @deprecated Some text.
         */
        String method6(String aString, int aInt, boolean aBoolean) throws Exception
        {
            return "null";
        }
    }

    InnerClassWithAnnotations anon = new InnerClassWithAnnotations()
    {
        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @throws Exception Some text.
         * @deprecated Some text.
         */
        String method(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @throws Exception Some text.
         */
        String method1(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @throws Exception Some text.
         * @serialData Some javadoc.
         */
        void method2(String aString) throws Exception {}

        /**
         * Some text.
         * @throws Exception Some text.
         * @deprecated Some text.
         */
        void method3() throws Exception {}

        /**
         * Some text.
         * @return Some text.
         * @throws Exception Some text.
         */
        String method4() throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @deprecated Some text.
         */
        String method5(String aString)
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @param aInt Some text.
         * @param aBoolean Some text.
         * @return Some text.
         * @throws Exception Some text.
         * @deprecated Some text.
         */
        String method6(String aString, int aInt, boolean aBoolean) throws Exception
        {
            return "null";
        }
    };
}

/**
 * Some javadoc.
 *
 * @author max
 * @version 1.0
 * @see Some javadoc.
 * @since Some javadoc.
 * @deprecated Some javadoc.
 */
enum Foo3 {}

/**
 * Some javadoc.
 *
 * @author max
 * @version 1.0
 * @see Some javadoc.
 * @since Some javadoc.
 * @deprecated Some javadoc.
 */
interface FooIn3 {}
