/*
AtclauseOrder
violateExecutionOnNonTightHtml = (default)false
target = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
tagOrder = (default)@author, @version, @param, @return, @throws, @exception, \
            @see, @since, @serial, @serialField, @serialData, @deprecated


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.atclauseorder;

import java.io.Serializable;

public class InputAtclauseOrderCorrect4 implements Serializable {
     /**
     *
     * @author max
     * @version 1.0
     * @since Some javadoc.
     */
    class InnerClassWithAnnotations4 {

         /**
          * Some text.
          *
          * @param aString Some text.
          * @return Some text.
          * @serialData Some javadoc.
          * @deprecated Some text.
          */
         String method5(String aString) { // ok
             return "null";
         }

         /**
          * Some text.
          *
          * @param aString  Some text.
          * @param aInt     Some text.
          * @param aBoolean Some text.
          * @return Some text.
          * @throws Exception Some text.
          * @deprecated Some text.
          */
         String method6(String aString, int aInt, boolean aBoolean) throws Exception {
             return "null";
         }
    }
    InnerClassWithAnnotations4 anon = new InnerClassWithAnnotations4() {
        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @deprecated Some text.
         */
        String method5(String aString)
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @param aInt Some text.
         * @param aBoolean Some text.
         * @return Some text.
         * @throws Exception Some text.
         * @deprecated Some text.
         */
        String method6(String aString, int aInt, boolean aBoolean) throws Exception
        {
            return "null";
        }
    };
}

/**
 * Some javadoc.
 *
 * @author max
 * @version 1.0
 * @see Some javadoc.
 * @since Some javadoc.
 * @deprecated Some javadoc.
 */
enum Foo3 {}

/**
 * Some javadoc.
 *
 * @author max
 * @version 1.0
 * @see Some javadoc.
 * @since Some javadoc.
 * @deprecated Some javadoc.
 */
interface FooIn3 {}

