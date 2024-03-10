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
class InputAtclauseOrderIncorrectCustom1 implements Serializable
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
     * @serialData Some javadoc.
     * @deprecated Some text.
     * @throws Exception Some text.
     */
    String method(String aString) throws Exception {
        return "null";
    }

    /**
     * Some text.
     * @serialData Some javadoc.
     * @return Some text.
     * @param aString Some text.
     * @throws Exception Some text.
     */
    String method1(String aString) throws Exception {
        return "null";
    }

    /**
     * Some text.
     * @throws Exception Some text.
     * @param aString Some text.
     */
    void method2(String aString) throws Exception {}

    /**
     * Some text.
     * @deprecated Some text.
     * @throws Exception Some text.
     */
    void method3() throws Exception {}

    /**
     * Some text.
     * @return Some text.
     * @throws Exception Some text.
     */
    String method4() throws Exception {
        return "null";
    }

    /**
     * Some text.
     * @deprecated Some text.
     * @return Some text.
     * @param aString Some text.
     */
    String method5(String aString) {
        return "null";
    }

    /**
     * Some text.
     * @param aString Some text.
     * @return Some text.
     * @serialData Some javadoc.
     * @param aInt Some text.
     * @throws Exception Some text.
     * @param aBoolean Some text.
     * @deprecated Some text.
     */
    String method6(String aString, int aInt, boolean aBoolean) throws Exception {
        return "null";
    }
}
