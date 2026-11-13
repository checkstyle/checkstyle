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

public class InputEmptyLineSeparatorWithCommentsFields {


    public int testViolationWithoutComment = 1; // violation ''VARIABLE_DEF' .*1 empty lines .*'

    public int testNoViolationWithoutComment = 2;


    // violation ''//' has more than 1 empty lines before.'
    public int testViolationWithSingleLineComment = 3;

    // Should be ok
    public int testNoViolationWithSingleLineComment = 4;


    // violation ''//' has more than 1 empty lines before.'
    /*
     * Should have
     * violation
     */
    public int testViolationWithMultilineComment = 5;

    /*
     * Should not have
     * violation
     */
    public int testNoViolationWithMultilineComment = 6;


    // violation ''//' has more than 1 empty lines before.'
    /**
     * Should have
     * violation
     */
    public int testViolationWithJavadoc = 7;

    /**
     * Should not have
     * violation
     */
    public int testNoViolationWithJavadoc = 8;


    // violation ''//' has more than 1 empty lines before.'
    // .
    /* . */ public InputEmptyLineSeparatorWithCommentsFields() {
        testNoViolationWithJavadoc = 1;
    }

    // ok, because no more than 1 empty lines before
    /* . */ public InputEmptyLineSeparatorWithCommentsFields(int i) {
        testNoViolationWithJavadoc = 1;
    }

    // ok, because no more than 1 empty lines before
    // ok, because no more than 1 empty lines before

    // ok, because no more than 1 empty lines before
    public InputEmptyLineSeparatorWithCommentsFields(int i, int j) {
        testNoViolationWithJavadoc = 1;
    }


    // violation ''//' has more than 1 empty lines before.'
    // ok, because no more than 1 empty lines before

    // ok, because no more than 1 empty lines before




    // violation ''//' has more than 1 empty lines before.'
    public InputEmptyLineSeparatorWithCommentsFields(int i, int j, int k) {
        testNoViolationWithJavadoc = 1;
    }
}
