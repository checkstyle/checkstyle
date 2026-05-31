/*
AtclauseOrder
violateExecutionOnNonTightHtml = (default)false
target = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
tagOrder = (default)@author, @version, @param, @return, @throws, @exception, @see, @since, \
           @serial, @serialField, @serialData, @deprecated


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.atclauseorder;

import java.io.Serializable;

public class InputAtclauseOrderRecords {
    public record MyRecord(int x) implements Serializable {
        private static final long serialVersionUID = 3928773301716751506L;

        /**
         * Some text.
         *
         * @param aString Some text. // should be a violation, but doesn't work w/ anno
         * @return Some text.
         * @throws Exception Some text.
         */
        String method(String aString) throws Exception {
            return "null";
        }

        // violation 7 lines below
        // violation 7 lines below
        // violation 7 lines below
        /**
         * Some text.
         *
         * @since the other day
         * @param aString Some text.
         * @return Some text.
         * @throws Exception Some text.
         */
        String method1(String aString) throws Exception {
            return "null";
        }

        // violation 6 lines below
        // violation 6 lines below
        /**
         * Some text.
         *
         * @since some time
         * @param aString Some text.
         * @throws Exception Some text.
         * @serialData Some javadoc.
         */
        void method2(String aString) throws Exception {
        }

        // violation 4 lines below
        /**
         * Some text.
         * @since since
         * @throws Exception Some text.
         * @since Some text.
         */
        void method3() throws Exception {
        }
    }

}

/**
 * Some javadoc.
 *
 * @author max // should be a violation
 * @version 1.0
 * @since Some javadoc.
 */
record myOtherOtherRecord() {
    // violation 3 lines below
    /**
     * @since Some javadoc.
     * @author max
     **/
    public myOtherOtherRecord{}
}

/**
 * Some javadoc.
 *
 * @author max // should be a violation
 * @version 1.0
 * @since Some javadoc.
 */
class myOtherOtherClass {
    // violation 3 lines below
    /**
     * @since Some javadoc.
     * @author max
     **/
    public myOtherOtherClass() {
    }
}
