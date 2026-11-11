/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, STATIC_INIT, INSTANCE_INIT, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public class InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeCommentsMethods {

    public void testNoViolationMethod1() {
    }

    public void testNoViolationMethod2() {
    }

    // Should not have

    public void testNoViolationMethod3() {




    }

    /* no violation */

    /*
     * Should not have
     * violation
     */
    public void testNoViolationMethod4() {

    }

    /*
     * Should not have
     * violation
     */
    public void testNoViolationMethod5() {




        /*
         * no
         * violation
         */


    }






    /**
     * Should have
     * violation
     */
    public void testNoViolationWithJavadoc1() {






        /* no violtaion */





    }

    /**
     * Should not have
     * violation
     */
    public void testNoViolationWithJavadoc2() {
    }
}
