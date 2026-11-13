/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, STATIC_INIT, INSTANCE_INIT, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public class InputEmptyLineSeparatorWithCommentsMethods {


    public void testViolationWithoutComment() { // violation ''METHOD_DEF' .*1 empty lines .*'
    }

    public void testNoViolationWithoutComment() {
    }


    // violation ''//' has more than 1 empty lines before.'
    public void testViolationWithSingleLineComment() {
    }

    // Should be ok

    public void testNoViolationWithSingleLineComment() {
    }


    // violation ''//' has more than 1 empty lines before.'
    /*
     * Should have
     * violation
     */
    public void testViolationWithMultilineComment() {
    }

    /*
     * Should not have
     * violation
     */
    public void testNoViolationWithMultilineComment() {
    }


    // violation ''//' has more than 1 empty lines before.'
    /**
     * Should have
     * violation
     */
    public void testViolationWithJavadoc() {
    }

    /**
     * Should not have
     * violation
     */
    public void testNoViolationWithJavadoc() {
    }
}
