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
class InputAtclauseOrderIncorrect4 implements Serializable {
    // violation 7 lines below 'Block tags have to appear in the order .\[@author.*'
    /**
     * Some javadoc.
     *
     * @version 1.0
     * @since Some javadoc.
     * @serialData Some javadoc.
     * @author max
     */
    // violation 6 lines below 'Block tags have to appear in the order .\[@author.*'
    class InnerClassWithAnnotations4 {
        /**
         * Some text.
         * @param aString Some text.
         * @deprecated Some text.
         * @return Some text.
         */
        String method5(String aString) {
            return "null";
        }
        // violation 6 lines below 'Block tags have to appear in the order .\[@author.*'
        // violation 7 lines below 'Block tags have to appear in the order .\[@author.*'
        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @param aInt Some text.
         * @throws Exception Some text.
         * @param aBoolean Some text.
         * @deprecated Some text.
         */
        String method6(String aString, int aInt, boolean aBoolean) throws Exception {
            return "null";
        }
    }
    InnerClassWithAnnotations4 anon = new InnerClassWithAnnotations4() {
        // violation 5 lines below 'Block tags have to appear in the order .\[@author.*'
        // violation 5 lines below 'Block tags have to appear in the order .\[@author.*'
        /**
         * Some text.
         * @deprecated Some text.
         * @return Some text.
         * @param aString Some text.
         */
        String method5(String aString) {
            return "null";
        }

        // violation 6 lines below 'Block tags have to appear in the order .\[@author.*'
        // violation 7 lines below 'Block tags have to appear in the order .\[@author.*'
        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @param aInt Some text.
         * @throws Exception Some text.
         * @param aBoolean Some text.
         * @deprecated Some text.
         */
        String method6(String aString, int aInt, boolean aBoolean) throws Exception {
            return "null";
        }
    };
}
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
enum Foo4 {}
// violation 7 lines below 'Block tags have to appear in the order .\[@author.*'
/**
 * Some javadoc.
 *
 * @version 1.0
 * @since Some javadoc.
 * @serialData Some javadoc.
 * @author max
 */
interface FooIn {
    /**
     * @value tag without specified order by default
     */
    int CONSTANT = 0;
}
