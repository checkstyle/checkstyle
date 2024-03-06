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
class InputAtclauseOrderIncorrect4 implements Serializable
{
    /**
     * Some javadoc.
     *
     * @version 1.0
     * @since Some javadoc.
     * @serialData Some javadoc.
     * @author max // violation
     */
    class InnerClassWithAnnotations4 {
        /**
         * Some text.
         * @param aString Some text.
         * @deprecated Some text.
         * @return Some text. // violation
         */
        String method5(String aString) {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @param aInt Some text. // violation
         * @throws Exception Some text.
         * @param aBoolean Some text. // violation
         * @deprecated Some text.
         */
        String method6(String aString, int aInt, boolean aBoolean) throws Exception {
            return "null";
        }
    }

    InnerClassWithAnnotations4 anon = new InnerClassWithAnnotations4()
    {
        /**
         * Some text.
         * @deprecated Some text.
         * @return Some text. // violation
         * @param aString Some text. // violation
         */
        String method5(String aString) {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @param aInt Some text. // violation
         * @throws Exception Some text.
         * @param aBoolean Some text. // violation
         * @deprecated Some text.
         */
        String method6(String aString, int aInt, boolean aBoolean) throws Exception {
            return "null";
        }
    };
}

/**
 * Some javadoc.
 *
 * @since Some javadoc.
 * @version 1.0 // violation
 * @deprecated Some javadoc.
 * @see Some javadoc. // violation
 * @author max // violation
 */
enum Foo4 {}

/**
 * Some javadoc.
 *
 * @version 1.0
 * @since Some javadoc.
 * @serialData Some javadoc.
 * @author max // violation
 */
interface FooIn {
    /**
     * @value tag without specified order by default
     */
    int CONSTANT = 0;
}
