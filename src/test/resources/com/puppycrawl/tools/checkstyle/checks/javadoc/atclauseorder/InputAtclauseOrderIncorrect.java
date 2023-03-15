/*
AtclauseOrder
violateExecutionOnNonTightHtml = (default)false
target = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
tagOrder = (default)@author, @deprecated, @exception, @param, @return, \
           @see, @serial, @serialData, @serialField, @since, @throws, @version


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.atclauseorder;
/** Javadoc for import */
import java.io.Serializable;

/**
 * Some javadoc.
 *
 * @since Some javadoc.
 * @version 1.0 // violation
 * @deprecated Some javadoc.
 * @see Some javadoc. // violation
 * @author max // violation
 */
class InputAtclauseOrderIncorrect implements Serializable
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
     * @serialData Some javadoc.
     * @deprecated Some text.
     * @throws Exception Some text. // violation
     */
    String method(String aString) throws Exception
    {
        return "null";
    }

    /**
     * Some text.
     * @serialData Some javadoc.
     * @return Some text. // violation
     * @param aString Some text. // violation
     * @throws Exception Some text. // violation
     */
    String method1(String aString) throws Exception
    {
        return "null";
    }

    /**
     * Some text.
     * @throws Exception Some text.
     * @param aString Some text. // violation
     */
    void method2(String aString) throws Exception {}

    /**
     * Some text.
     * @deprecated Some text.
     * @throws Exception Some text. // violation
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
     * @deprecated Some text.
     * @return Some text. // violation
     * @param aString Some text. // violation
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
     * @param aInt Some text. // violation
     * @throws Exception Some text. // violation
     * @param aBoolean Some text. // violation
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
     * @author max // violation
     */
    class InnerClassWithAnnotations
    {
        /**
         * Some text.
         * @return Some text.
         * @deprecated Some text.
         * @param aString Some text. // violation
         * @throws Exception Some text. // violation
         */
        String method(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @throws Exception Some text.
         * @return Some text. // violation
         * @param aString Some text. // violation
         */
        String method1(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @serialData Some javadoc.
         * @param aString Some text. // violation
         * @throws Exception Some text. // violation
         */
        void method2(String aString) throws Exception {}

        /**
         * Some text.
         * @deprecated Some text.
         * @throws Exception Some text. // violation
         */
        void method3() throws Exception {}

        /**
         * Some text.
         * @throws Exception Some text.
         * @serialData Some javadoc.
         * @return Some text. // violation
         */
        String method4() throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @deprecated Some text.
         * @return Some text. // violation
         */
        String method5(String aString)
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @param aInt Some text. // violation
         * @throws Exception Some text.
         * @param aBoolean Some text. // violation
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
         * @throws Exception Some text.
         * @param aString Some text. // violation
         * @serialData Some javadoc.
         * @deprecated Some text.
         * @return Some text. // violation
         */
        String method(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @throws Exception Some text.
         * @return Some text. // violation
         */
        String method1(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @throws Exception Some text.
         * @param aString Some text. // violation
         */
        void method2(String aString) throws Exception {}

        /**
         * Some text.
         * @deprecated Some text.
         * @throws Exception Some text. // violation
         */
        void method3() throws Exception {}

        /**
         * Some text.
         * @throws Exception Some text.
         * @return Some text. // violation
         */
        String method4() throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @deprecated Some text.
         * @return Some text. // violation
         * @param aString Some text. // violation
         */
        String method5(String aString)
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @param aInt Some text. // violation
         * @throws Exception Some text.
         * @param aBoolean Some text. // violation
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
 * @since Some javadoc.
 * @version 1.0 // violation
 * @deprecated Some javadoc.
 * @see Some javadoc. // violation
 * @author max // violation
 */
enum Foo4 {}

/**
 * Some javadoc.
 *
 * @version 1.0
 * @since Some javadoc.
 * @serialData Some javadoc.
 * @author max // violation
 */
interface FooIn {
    /**
     * @value tag without specified order by default
     */
    int CONSTANT = 0;
}
