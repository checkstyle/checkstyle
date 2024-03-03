/*
AtclauseOrder
violateExecutionOnNonTightHtml = (default)false
target = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
tagOrder = (default)@author, @version, @param, @return, @throws, @exception, \
            @see, @since, @serial, @serialField, @serialData, @deprecated


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.atclauseorder;

public class InputAtclauseOrderWithAnnotationsOutsideJavadoc {
    /**
     * Some javadoc.
     *
     * @author max
     * @deprecated Some javadoc.
     * @see Some javadoc.   // violation
     * @version 1.0         // violation
     * @since Some javadoc. // violation
     */
    @Deprecated
    public boolean branchContains(int type) { return true; }

    /**
     * Some javadoc.
     *
     * @author max
     * @deprecated Some javadoc.
     * @see Some javadoc.   // violation
     * @version 1.0         // violation
     * @since Some javadoc. // violation
     */
    public boolean branchContains2(int type) { return true; }
}

/**
 * Some javadoc.
 *
 * @author max
 * @deprecated Some javadoc.
 * @see Some javadoc.   // violation
 * @version 1.0         // violation
 * @since Some javadoc. // violation
 */
@Deprecated
class TestClass {

}

class TestInnerClasses extends InputAtclauseOrderWithAnnotationsOutsideJavadoc{
    /**
     * Some javadoc.
     *
     * @author max
     * @deprecated Some javadoc.
     * @see Some javadoc.   // violation
     * @version 1.0         // violation
     * @since Some javadoc. // violation
     */
    @Deprecated
    TestClass one = new TestClass(){

    };

    /**
     * Some javadoc.
     *
     * @author max
     * @deprecated Some javadoc.
     * @see Some javadoc.   // violation
     * @version 1.0         // violation
     * @since Some javadoc. // violation
     */
    @Override
    public boolean branchContains(int type) {
        return false;
    }
}

/**
 * Some javadoc.
 *
 * @author max
 * @deprecated Some javadoc.
 * @see Some javadoc.   // violation
 * @version 1.0         // violation
 * @since Some javadoc. // violation
 */
@Deprecated
enum TestEnums {}

/**
 * Some javadoc.
 *
 * @author max
 * @deprecated Some javadoc.
 * @see Some javadoc.   // violation
 * @version 1.0         // violation
 * @since Some javadoc. // violation
 */
@Deprecated
interface TestInterfaces {}
