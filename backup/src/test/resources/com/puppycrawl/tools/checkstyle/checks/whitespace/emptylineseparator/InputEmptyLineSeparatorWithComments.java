/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator; // violation ''package' should be separated from previous line.'



// violation ''//' has more than 1 empty lines before.'



import java.lang.Object; // violation ''import' has more than 1 empty lines before.'

import java.lang.Class; // no violation


// violation ''//' has more than 1 empty lines before.'
import java.lang.Integer;

// no violation
import java.lang.Long;


/* // violation ''/\*' has more than 1 empty lines before.'
 *
 */
import java.lang.Float;

/*
 * no violation
 */
import java.lang.Double;


/** // violation ''/\*' has more than 1 empty lines before.'
 *
 */
import java.lang.Character;

/**
 * no violation
 */
import java.lang.String;

/*
 * no violation
 */
/* no violation */
import java.lang.Object;


// violation ''//' has more than 1 empty lines before.'
// .
import java.lang.Boolean;
// no violation
import java.lang.Byte;

// no violation
/* no violation */
import java.lang.Short;


/* violation */
// violation above ''/\*' has more than 1 empty lines before.'
import java.lang.Number;


import java.lang.Runnable; // violation ''import' has more than 1 empty lines before.'
import java.lang.Thread;

// no violation


// violation ''//' has more than 1 empty lines before.'
import java.lang.StringBuilder;


/* // violation ''/\*' has more than 1 empty lines before.'
 *
 */
import static java.lang.Math.abs;

public class InputEmptyLineSeparatorWithComments {


    public int testViolationWithoutComment = 1; // violation ''VARIABLE_DEF' .*1 empty lines .*'

    public int testNoViolationWithoutComment = 2; // no violation


    // violation ''//' has more than 1 empty lines before.'
    public int testViolationWithSingleLineComment = 3;

    // Should not have violations
    public int testNoViolationWithSingleLineComment = 4;


    /* // violation ''/\*' has more than 1 empty lines before.'
     * Should have
     * violation
     */
    public int testViolationWithMultilineComment = 5;

    /*
     * Should not have
     * violation
     */
    public int testNoViolationWithMultilineComment = 6;


    /** // violation ''/\*' has more than 1 empty lines before.'
     * Should have
     * violation
     */
    public int testViolationWithJavadoc = 7;

    /**
     * Should not have
     * violation
     */
    public int testNoViolationWithJavadoc = 8;


    public void testViolationWithoutComment() { // violation ''METHOD_DEF' .*1 empty lines .*'
    }

    public void testNoViolationWithoutComment() {
    } // no violation


    // violation ''//' has more than 1 empty lines before.'
    public void testViolationWithSingleLineComment() {
    }

    // Should not have
    // a violation
    public void testNoViolationWithSingleLineComment() {
    }


    /*// violation ''/\*' has more than 1 empty lines before.'
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


    /** // violation ''/\*' has more than 1 empty lines before.'
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

    public static class Class1 { } // no violation


    public static class Class2 { } // violation ''CLASS_DEF' has more than 1 empty lines before.'

    // no violation
    public static class Class3 { }


    // violation ''//' has more than 1 empty lines before.'
    public static class Class4 { }


    // violation ''//' has more than 1 empty lines before.'
    public
    // no violation
    static class Class5 { }


    // violation ''//' has more than 1 empty lines before.'
    public
    /* no violation */
    static class Class6 { }

    /*
     * Should not have
     * violation
     */
    public static class Class7 { }


    /* // violation ''/\*' has more than 1 empty lines before.'
     * Should have
     * violation
     */
    public static class Class8 { }

    /**
     * Should not have
     * violation
     */
    public static class Class9 { }


    /** // violation ''/\*' has more than 1 empty lines before.'
     * Should have
     * violation
     */
    public static class Class10 {
        {
            // no violation
        }
    }

    // no violation
    public interface Interface1 { }


    // violation ''//' has more than 1 empty lines before.'
    public interface Interface2 { }
    // violation below ''INTERFACE_DEF' should be separated from previous line.'
    public
    // .
    interface Interface3 { }


    /* // violation ''/\*' has more than 1 empty lines before.'
     */
    /* . */
    /* . */
    /* . */
    interface Interface4 { }

    // no violation

    // no violation
    // .
    // .
    // .
    interface Interface5 { }


    // violation ''//' has more than 1 empty lines before.'
    public enum Enum1 {
        E1, E2
    }

    // no violation
    public enum Enum2 { }


    // violation ''//' has more than 1 empty lines before.'
    // no violation

    // no violation
    public enum Enum3 { }
    // no violation

    public enum Enum4 { }
    // no violation

    public enum Enum5 { }


    // violation ''//' has more than 1 empty lines before.'

    public


    // violation ''//' has more than 1 empty lines before.'
    static

    enum Enum6 { }


    // violation ''//' has more than 1 empty lines before.'
    static {
        abs(2);
    }

    // no violation


    // violation ''//' has more than 1 empty lines before.'
    {
       abs(1);
    }

    /* no violation */
    { }


    // violation ''//' has more than 1 empty lines before.'
    {
        int i = 1;
    }


    // violation ''//' has more than 1 empty lines before.'
    // .
    /* . */ public InputEmptyLineSeparatorWithComments() {
        testNoViolationWithJavadoc = 1;
    }

    // no violation
    /* . */ public InputEmptyLineSeparatorWithComments(int i) {
        testNoViolationWithJavadoc = 1;
    }

    // no violation
    // no violation

    // no violation
    public InputEmptyLineSeparatorWithComments(int i, int j) {
        testNoViolationWithJavadoc = 1;
    }


    // violation ''//' has more than 1 empty lines before.'
    // no violation

    // no violation




    // violation ''//' has more than 1 empty lines before.'
    public InputEmptyLineSeparatorWithComments(int i, int j, int k) {
        testNoViolationWithJavadoc = 1;
    }

}
