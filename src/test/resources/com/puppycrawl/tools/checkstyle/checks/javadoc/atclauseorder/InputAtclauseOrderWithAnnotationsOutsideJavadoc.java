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
    // violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
    // violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
    // violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
    /**
     * Some javadoc.
     *
     * @author max
     * @deprecated Some javadoc.
     * @see Some javadoc.
     * @version 1.0
     * @since Some javadoc.
     */
    @Deprecated
    public boolean branchContains(int type) { return true; }

    // violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
    // violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
    // violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
    /**
     * Some javadoc.
     *
     * @author max
     * @deprecated Some javadoc.
     * @see Some javadoc.
     * @version 1.0
     * @since Some javadoc.
     */
    public boolean branchContains2(int type) { return true; }
}

// violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
// violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
// violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
/**
 * Some javadoc.
 *
 * @author max
 * @deprecated Some javadoc.
 * @see Some javadoc.
 * @version 1.0
 * @since Some javadoc.
 */
@Deprecated
class TestClass {}

class TestInnerClasses extends InputAtclauseOrderWithAnnotationsOutsideJavadoc{
    // violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
    // violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
    // violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
    /**
     * Some javadoc.
     *
     * @author max
     * @deprecated Some javadoc.
     * @see Some javadoc.
     * @version 1.0
     * @since Some javadoc.
     */
    @Deprecated
    TestClass one = new TestClass(){};
    // violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
    // violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
    // violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
    /**
     * Some javadoc.
     *
     * @author max
     * @deprecated Some javadoc.
     * @see Some javadoc.
     * @version 1.0
     * @since Some javadoc.
     */
    @Override
    public boolean branchContains(int type) {
        return false;
    }
}
// violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
// violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
// violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
/**
 * Some javadoc.
 *
 * @author max
 * @deprecated Some javadoc.
 * @see Some javadoc.
 * @version 1.0
 * @since Some javadoc.
 */
@Deprecated
enum TestEnums {}

// violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
// violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
// violation 8 lines below 'Block tags have to appear in the order .\[@author.*'
/**
 * Some javadoc.
 *
 * @author max
 * @deprecated Some javadoc.
 * @see Some javadoc.
 * @version 1.0
 * @since Some javadoc.
 */
@Deprecated
interface TestInterfaces {}
