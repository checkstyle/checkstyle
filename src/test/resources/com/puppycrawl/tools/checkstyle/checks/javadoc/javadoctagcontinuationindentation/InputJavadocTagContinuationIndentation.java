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

    // violation 6 lines below 'Line continuation .* expected level should be 4'
    // violation 8 lines below 'Line continuation .* expected level should be 4'
    /**
     * Some text.
     * @param aString Some text.
     * @return Some text.
     *    Some javadoc.
     * @serialData Some javadoc.
     * @param aInt Some text.
     *    Some javadoc.
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

        // violation 7 lines below 'Line continuation .* expected level should be 4'
        // violation 9 lines below 'Line continuation .* expected level should be 4'
        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @param aInt Some text.
         *    Some javadoc.
         * @throws Exception Some text.
         * @param aBoolean Some text.
         *    Some javadoc.
         * @see Some text.
         */
        String method6(String aString, int aInt, boolean aBoolean) throws Exception
        {
            return "null";
        }
    }

    InnerClassWithAnnotations anon = new InnerClassWithAnnotations()
    {
        // violation 6 lines below 'Line continuation .* expected level should be 4'
        // violation 7 lines below 'Line continuation .* expected level should be 4'
        /**
         * Some text.
         * @throws Exception Some text.
         * @param aString Some text.
         *   Some javadoc.
         * @serialData Some javadoc.
         *    Some javadoc.
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

        // violation 7 lines below 'Line continuation .* expected level should be 4'
        // violation 9 lines below 'Line continuation .* expected level should be 4'
        // violation 10 lines below 'Line continuation .* expected level should be 4'
        /**
         * Some text.
         *       Some javadoc.
         * @param aString Some text.
         *    Some javadoc.
         * @return Some text.
         * @param aInt Some text.
         *    Some javadoc.
         * @throws Exception Some text.
         *    Some javadoc.
         * @param aBoolean Some text.
         * @see Some text.
         */
        String method6(String aString, int aInt, boolean aBoolean) throws Exception
        {
            return "null";
        }
    };
}

// violation 10 lines below 'Line continuation .* expected level should be 4'
/**
 * Some javadoc.
 *
 * @since Some javadoc.
 * @version 1.0
 * @see Some javadoc.
 *     Some javadoc.
 *     Some javadoc.
 * @see Some javadoc.
 *    Some javadoc.
 * @author max
 */
enum Foo1 {}

// violation 9 lines below 'Line continuation .* expected level should be 4'
// violation 10 lines below 'Line continuation .* expected level should be 4'
/**
 * Some javadoc.
 *
 * @version 1.0
 * @since Some javadoc.
 *     Some javadoc.
 * @serialData Some javadoc.
 *   Line below is empty on purpose.
 * @see Some Text.
 * L.
 *
 * @author max
 * @customTag {@link com.puppycrawl.tools.checkstyle.AllChecksPresentOnAvailableChecksPageTest
 *   some description} // ok, as this is just inline tag description
 */
interface FooIn1 {}

/**
 * <p>Testing javadoc with spanning tag {@linkplain #DEFAULT default mapping
 * factory}.</p>
 */
interface FooIn2 {}
class ShortNextLine {
    // violation 5 lines below 'Line continuation .* expected level should be 4'
    /**
     * Test.
     *
     * @return Test
     * tt <code>null</code>.
     */
    public void example() {
    }
}
