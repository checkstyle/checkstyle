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
class InputAtclauseOrderIncorrect1 implements Serializable {
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
    // violation 7 lines below 'Block tags have to appear in the order .\[@author.*'
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
    // violation 6 lines below 'Block tags have to appear in the order .\[@author.*'
    // violation 6 lines below 'Block tags have to appear in the order .\[@author.*'
    // violation 6 lines below 'Block tags have to appear in the order .\[@author.*'
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
    // violation 4 lines below 'Block tags have to appear in the order .\[@author.*'
    /**
     * Some text.
     * @throws Exception Some text.
     * @param aString Some text.
     */
    void method2(String aString) throws Exception {}
    // violation 4 lines below 'Block tags have to appear in the order .\[@author.*'
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
    // violation 5 lines below 'Block tags have to appear in the order .\[@author.*'
    // violation 5 lines below 'Block tags have to appear in the order .\[@author.*'
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
    // violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
    // violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
    // violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
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
