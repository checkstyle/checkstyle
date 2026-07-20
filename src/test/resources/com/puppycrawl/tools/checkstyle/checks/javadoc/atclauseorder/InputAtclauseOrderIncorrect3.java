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

// violation 7 lines below 'Block tags have to appear in the order .\[@author.*'
// violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
// violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
/**
 * Some javadoc.
 *
 * @since Some javadoc.
 * @version 1.0
 * @deprecated Some javadoc.
 * @see Some javadoc.
 * @author max
 */
class InputAtclauseOrderIncorrect3 implements Serializable
{
    // violation 7 lines below 'Block tags have to appear in the order .\[@author.*'
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
        // violation 4 lines below 'Block tags have to appear in the order .\[@author.*'
        /**
         * Some text.
         * @deprecated Some text.
         * @throws Exception Some text.
         */
        void method3() throws Exception {}

        // violation 5 lines below 'Block tags have to appear in the order .\[@author.*'
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
        // violation 4 lines below 'Block tags have to appear in the order .\[@author.*'
        /**
         * Some text.
         * @deprecated Some text.
         * @throws Exception Some text.
         */
        void method3() throws Exception {}

        // violation 4 lines below 'Block tags have to appear in the order .\[@author.*'
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
