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

/**
 * Some javadoc.
 *
 * @author max
 * @version 1.0
 * @see Some javadoc.
 * @since Some javadoc.
 * @deprecated Some javadoc. // ok
 */
class InputAtclauseOrderCorrect1 implements Serializable
{
    /**
     * The client's first name.
     * @serial
     */
    private String fFirstName;

    /**
     * The client's first name.
     * @serial
     */
    private String sSecondName;

    /**
     * The client's first name.
     * @serialField
     */
    private String tThirdName;

    /**
     * Some text.
     * @param aString Some text.
     * @return Some text.
     * @throws Exception Some text.
     * @serialData Some javadoc.
     * @deprecated Some text.
     */
    String method(String aString) throws Exception
    {
        return "null";
    }

    /**
     * Some text.
     * @param aString Some text.
     * @return Some text.
     * @throws Exception Some text.
     * @serialData Some javadoc.
     */
    String method1(String aString) throws Exception
    {
        return "null";
    }

    /**
     * Some text.
     * @param aString Some text.
     * @throws Exception Some text.
     */
    void method2(String aString) throws Exception {}

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

    /**
     * Some text.
     * @param aString Some text.
     * @return Some text.
     * @serialData Some javadoc.
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
}
