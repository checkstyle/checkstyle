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

public class InputEmptyLineSeparatorWithComments2Methods {


    public void testViolationWithoutComment() {
    }

    public void testNoViolationWithoutComment() {
    }


    // Should be ok
    public void testViolationWithSingleLineComment() {
    }

    // Should not have
    // something
    public void testNoViolationWithSingleLineComment() {
    }


    /*
     * Should have
     * something
     */
    public void testViolationWithMultilineComment() {
    }

    /*
     * Should not have
     * something
     */
    public void testNoViolationWithMultilineComment() {
    }


    /**
     * Should have
     * something
     */
    public void testViolationWithJavadoc() {
    }

    /**
     * Should not have
     * something
     */
    public void testNoViolationWithJavadoc() {
    }
}
