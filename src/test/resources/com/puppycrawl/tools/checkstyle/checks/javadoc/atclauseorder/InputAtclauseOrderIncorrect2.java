/*
AtclauseOrder
violateExecutionOnNonTightHtml = (default)false
target = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
tagOrder = (default)@author, @version, @param, @return, @throws, @exception, \
            @see, @since, @serial, @serialField, @serialData, @deprecated


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
class InputAtclauseOrderIncorrect2 implements Serializable
{
    /**
     * Some javadoc.
     *
     * @version 1.0
     * @since Some javadoc.
     * @serialData Some javadoc.
     * @author max // violation
     */
    class InnerClassWithAnnotations2
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
    }

    InnerClassWithAnnotations2 anon = new InnerClassWithAnnotations2()
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
    };
}
