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

public class InputEmptyLineSeparatorWithComments2Fields {


    public int testViolationWithoutComment = 1;

    public int testNoViolationWithoutComment = 2;



    public int testViolationWithSingleLineComment = 3;


    public int testNoViolationWithSingleLineComment = 4;


    /*
     * Should have
     * something
     */
    public int testViolationWithMultilineComment = 5;

    /*
     * Should not have
     * something
     */
    public int testNoViolationWithMultilineComment = 6;


    /**
     * Should have
     * something
     */
    public int testViolationWithJavadoc = 7;

    /**
     * Should not have
     * something
     */
    public int testNoViolationWithJavadoc = 8;



    // .
    /* . */ public InputEmptyLineSeparatorWithComments2Fields() {
        testNoViolationWithJavadoc = 1;
    }


    /* . */ public InputEmptyLineSeparatorWithComments2Fields(int i) {
        testNoViolationWithJavadoc = 1;
    }





    public InputEmptyLineSeparatorWithComments2Fields(int i, int j) {
        testNoViolationWithJavadoc = 1;
    }











    public InputEmptyLineSeparatorWithComments2Fields(int i, int j, int k) {
        testNoViolationWithJavadoc = 1;
    }
}
