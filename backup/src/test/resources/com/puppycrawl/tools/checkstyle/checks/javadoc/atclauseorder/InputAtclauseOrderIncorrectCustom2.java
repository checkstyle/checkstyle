/*
AtclauseOrder
violateExecutionOnNonTightHtml = (default)false
target = CLASS_DEF
tagOrder = @since, @version, @param, @return, @throws, @exception, @deprecated, @see, \
           @serial, @serialField, @serialData, @author


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.atclauseorder;
/** Javadoc for import */
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
class InputAtclauseOrderIncorrectCustom2 implements Serializable
{
    /**
     * Some javadoc.
     *
     * @version 1.0
     * @since Some javadoc. // violation
     * @serialData Some javadoc.
     * @author max
     */
    class InnerClassWithAnnotations2
    {
        /**
         * Some text.
         * @return Some text.
         * @deprecated Some text.
         * @param aString Some text.
         * @throws Exception Some text.
         */
        String method(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @throws Exception Some text.
         * @return Some text.
         * @param aString Some text.
         */
        String method1(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @serialData Some javadoc.
         * @param aString Some text.
         * @throws Exception Some text.
         */
        void method2(String aString) throws Exception {}
    }

    InnerClassWithAnnotations2 anon = new InnerClassWithAnnotations2()
    {
        /**
         * Some text.
         * @throws Exception Some text.
         * @param aString Some text.
         * @serialData Some javadoc.
         * @deprecated Some text.
         * @return Some text.
         */
        String method(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
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
         * @param aString Some text.
         */
        void method2(String aString) throws Exception {}
    };
}
