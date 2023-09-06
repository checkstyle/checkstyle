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

    /**
     * Some text.
     * @param aString Some javadoc.
     *     Some javadoc.
     * @return Some text.
     * @serialData Some javadoc.
     * @see Some text.
     *    Some javadoc. // violation
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
     * @see Some text.
     * @return Some text.
     * @param aString Some text.
     */
    String method5(String aString)
    {
        return "null";
    }

    /**
     * Some text.
     * @param aString Some text.
     * @return Some text.
     *    Some javadoc. // violation
     * @serialData Some javadoc.
     * @param aInt Some text.
     *    Some javadoc. // violation
     * @throws Exception Some text.
     * @param aBoolean Some text.
     * @see Some text.
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
    class InnerClassWithAnnotations
    {
        /**
         * Some text.
         * @return Some text.
         * @see Some text.
         *     Some javadoc.
         * @param aString Some text.
         * @throws Exception Some text.
         *     Some javadoc.
         */
        String method(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @throws Exception Some text.
         *     Some javadoc.
         * @return Some text.
         * @param aString Some text.
         *     Some javadoc.
         */
        String method1(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @serialData Some javadoc.
         *     Some javadoc.
         * @param aString Some text.
         *     Some javadoc.
         * @throws Exception Some text.
         */
        void method2(String aString) throws Exception {}

        /**
         * Some text.
         * @see Some text.
         * @throws Exception Some text.
         */
        void method3() throws Exception {}

        /**
         * Some text.
         * @throws Exception Some text.
         * @serialData Some javadoc.
         * @return Some text.
         */
        String method4() throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @see Some text.
         * @return Some text.
         */
        String method5(String aString)
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @param aInt Some text.
         *    Some javadoc. // violation
         * @throws Exception Some text.
         * @param aBoolean Some text.
         *    Some javadoc. // violation
         * @see Some text.
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
         * @throws Exception Some text.
         * @param aString Some text.
         *   Some javadoc. // violation
         * @serialData Some javadoc.
         *    Some javadoc. // violation
         * @see Some text.
         * @return Some text.
         */
        String method(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         *     Some javadoc.
         * @throws Exception Some text.
         * @return Some text.
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
         *     Some javadoc.
         * @throws Exception Some text.
         */
        void method3() throws Exception {}

        /**
         * Some text.
         * @throws Exception Some text.
         * @return Some text.
         */
        String method4() throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @see Some text.
         * @return Some text.
         * @param aString Some text.
         */
        String method5(String aString)
        {
            return "null";
        }

        /**
         * Some text.
         *       Some javadoc.
         * @param aString Some text.
         *    Some javadoc. // violation
         * @return Some text.
         * @param aInt Some text.
         *    Some javadoc. // violation
         * @throws Exception Some text.
         *    Some javadoc. // violation
         * @param aBoolean Some text.
         * @see Some text.
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
 * @since Some javadoc.
 * @version 1.0
 * @see Some javadoc.
 *     Some javadoc.
 *     Some javadoc.
 * @see Some javadoc.
 *    Some javadoc. // violation
 * @author max
 */
enum Foo1 {}

/**
 * Some javadoc.
 *
 * @version 1.0
 * @since Some javadoc.
 *     Some javadoc.
 * @serialData Some javadoc.
 *   Line below is empty on purpose. // violation
 * @see Some Text.
 * L. // violation
 *
 * @author max
 * @customTag {@link com.puppycrawl.tools.checkstyle.AllChecksPresentOnAvailableChecksPageTest
 *   some description} // ok, as this is just inline tag description
 */
interface FooIn1 {}

/**
 * <p>Testing javadoc with spanning tag {@linkplain #DEFAULT default mapping
 * factory}.</p> // ok
 */
interface FooIn2 {}
class ShortNextLine {
    /**
     * Test.
     *
     * @return Test
     * tt <code>null</code>. // violation
     */
    public void example() {
    }
}
