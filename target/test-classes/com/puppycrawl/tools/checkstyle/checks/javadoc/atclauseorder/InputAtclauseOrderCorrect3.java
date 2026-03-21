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

public class InputAtclauseOrderCorrect3 implements Serializable {
    /**
     *
     * @author max
     * @version 1.0
     * @since Some javadoc.
     */
    class InnerClassWithAnnotations3 {
        /**
         * Some text.
         *
         * @throws Exception Some text.
         * @deprecated Some text.
         */
        void method3() throws Exception {
        }

        /**
         * Some text.
         *
         * @return Some text.
         * @throws Exception Some text.
         * @serialData Some javadoc.
         */
        String method4() throws Exception {
            return "null";
        }
    }

    InnerClassWithAnnotations3 anon = new InnerClassWithAnnotations3() {
        /**
         * Some text.
         * @throws Exception Some text.
         * @deprecated Some text.
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
    };
}
