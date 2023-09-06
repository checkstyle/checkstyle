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
class InputAtclauseOrderIncorrect3 implements Serializable
{
    /**
     * Some javadoc.
     *
     * @version 1.0
     * @since Some javadoc.
     * @serialData Some javadoc.
     * @author max // violation
     */
    class InnerClassWithAnnotations3
    {
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
    }

    InnerClassWithAnnotations3 anon = new InnerClassWithAnnotations3()
    {
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
    };
}
