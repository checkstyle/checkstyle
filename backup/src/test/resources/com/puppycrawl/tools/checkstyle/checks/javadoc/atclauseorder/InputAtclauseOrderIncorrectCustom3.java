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
class InputAtclauseOrderIncorrectCustom3 implements Serializable
{
    /**
     * Some javadoc.
     *
     * @version 1.0
     * @since Some javadoc. // violation
     * @serialData Some javadoc.
     * @author max
     */
    class InnerClassWithAnnotations3
    {
        /**
         * Some text.
         * @deprecated Some text.
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
    }

    InnerClassWithAnnotations3 anon = new InnerClassWithAnnotations3()
    {
        /**
         * Some text.
         * @deprecated Some text.
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
    };
}
