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
class InputAtclauseOrderIncorrectCustom4 implements Serializable
{
    /**
     * Some javadoc.
     *
     * @version 1.0
     * @since Some javadoc. // violation
     * @serialData Some javadoc.
     * @author max
     */
    class InnerClassWithAnnotations4
    {
        /**
         * Some text.
         * @param aString Some text.
         * @deprecated Some text.
         * @return Some text.
         */
        String method5(String aString)
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @param aInt Some text.
         * @throws Exception Some text.
         * @param aBoolean Some text.
         * @deprecated Some text.
         */
        String method6(String aString, int aInt, boolean aBoolean) throws Exception
        {
            return "null";
        }
    }

    InnerClassWithAnnotations4 anon = new InnerClassWithAnnotations4()
    {
        /**
         * Some text.
         * @deprecated Some text.
         * @return Some text.
         * @param aString Some text.
         */
        String method5(String aString)
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @param aInt Some text.
         * @throws Exception Some text.
         * @param aBoolean Some text.
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
 * @since Some javadoc.
 * @version 1.0
 * @deprecated Some javadoc.
 * @see Some javadoc.
 * @author max
 */
enum Foo6 {}

/**
 * Some javadoc.
 *
 * @version 1.0
 * @since Some javadoc.
 * @serialData Some javadoc.
 * @author max
 */
interface FooInterface {
    /**
     * @value tag without specified order by default
     */
    int CONSTANT = 0;
}
