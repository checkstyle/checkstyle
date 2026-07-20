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
class InputAtclauseOrderIncorrect2 implements Serializable
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
    class InnerClassWithAnnotations2
    {
        // violation 6 lines below 'Block tags have to appear in the order .\[@author.*'
        // violation 6 lines below 'Block tags have to appear in the order .\[@author.*'
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

        // violation 5 lines below 'Block tags have to appear in the order .\[@author.*'
        // violation 5 lines below 'Block tags have to appear in the order .\[@author.*'
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

        // violation 5 lines below 'Block tags have to appear in the order .\[@author.*'
        // violation 5 lines below 'Block tags have to appear in the order .\[@author.*'
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
        // violation 5 lines below 'Block tags have to appear in the order .\[@author.*'
        // violation 7 lines below 'Block tags have to appear in the order .\[@author.*'
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

        // violation 5 lines below 'Block tags have to appear in the order .\[@author.*'
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

        // violation 4 lines below 'Block tags have to appear in the order .\[@author.*'
        /**
         * Some text.
         * @throws Exception Some text.
         * @param aString Some text.
         */
        void method2(String aString) throws Exception {}
    };
}
