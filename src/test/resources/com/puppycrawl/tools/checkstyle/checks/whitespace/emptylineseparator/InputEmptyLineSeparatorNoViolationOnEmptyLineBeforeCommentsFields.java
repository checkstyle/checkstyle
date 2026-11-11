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

public class InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeCommentsFields {

    public int testNoViolation1 = 1;

    public int testNoViolation2 = 2;

    // Should
    // not
    // have ok
    public int testNoViolation3 = 3;

    /*
     * Should not have
     * violation
     */
    public int testNoViolation4 = 4;

    /**
     * Should not have
     * violation
     */
    public int testNoViolationWithJavadoc = 5;


    // .
    /* . */ public InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeCommentsFields() {
        testNoViolationWithJavadoc = 1;






        /*
         * no
         * violation
         */


        // no

    }


    /* . */ public InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeCommentsFields(int i) {
        testNoViolationWithJavadoc = 1;

    }





    public InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeCommentsFields(int i, int j) {
        testNoViolationWithJavadoc = 1;
    }

    /* no violation */







    public InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeCommentsFields(int i, int j, int k) {
        testNoViolationWithJavadoc = 1;
    }
}
