/*
AtclauseOrder
violateExecutionOnNonTightHtml = (default)false
target = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
tagOrder = (default)@author, @deprecated, @exception, @param, @return, \
           @see, @serial, @serialData, @serialField, @since, @throws, @version


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.atclauseorder;

import java.io.Serializable;

public class InputAtclauseOrderCorrect2 implements Serializable {

    /**
     *
     * @author max
     * @version 1.0
     * @since Some javadoc.
     */
    class InnerClassWithAnnotations2
    {
        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @throws Exception Some text.
         * @deprecated Some text.
         */
        String method(String aString) throws Exception {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @throws Exception Some text.
         * @serialData Some javadoc.
         */
        String method1(String aString) throws Exception {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @throws Exception Some text.
         */
        void method2(String aString) throws Exception {}
    }

    InnerClassWithAnnotations2 anon = new InnerClassWithAnnotations2()
    {
        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @throws Exception Some text.
         * @deprecated Some text.
         */
        String method(String aString) throws Exception {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @throws Exception Some text.
         */
        String method1(String aString) throws Exception {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @throws Exception Some text.
         * @serialData Some javadoc.
         */
        void method2(String aString) throws Exception {}
    };
}
