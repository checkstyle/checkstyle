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

import java.lang.Class;


// violation ''//' has more than 1 empty lines before.'
import java.lang.Integer;

// ok, because no more than 1 empty lines before
import java.lang.Long;


/* // violation ''/\*' has more than 1 empty lines before.'
 *
 */
import java.lang.Float;

/*
 * something
 */
import java.lang.Double;


/** // violation ''/\*' has more than 1 empty lines before.'
 *
 */
import java.lang.Character;

/**
 * something
 */
import java.lang.String;

/*
 * something
 */
/* something */
import java.lang.Object;


// violation ''//' has more than 1 empty lines before.'
// .
import java.lang.Boolean;

import java.lang.Byte;

// ok, because no more than 1 empty lines before
/* something  */
import java.lang.Short;


/* something */
// violation above ''/\*' has more than 1 empty lines before.'
import java.lang.Number;


import java.lang.Runnable; // violation ''import' has more than 1 empty lines before.'
import java.lang.Thread;

// ok, because no more than 1 empty lines before


// violation ''//' has more than 1 empty lines before.'
import java.lang.StringBuilder;


/* // violation ''/\*' has more than 1 empty lines before.'
 *
 */
import static java.lang.Math.abs;

public class InputEmptyLineSeparatorWithComments {


    public int testViolationWithoutComment = 1; // violation ''VARIABLE_DEF' .*1 empty lines .*'

    public int testNoViolationWithoutComment = 2;


    // violation ''//' has more than 1 empty lines before.'
    public int testViolationWithSingleLineComment = 3;

    // Should be ok
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
    }


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

    public static class Class1 { }


    public static class Class2 { } // violation ''CLASS_DEF' has more than 1 empty lines before.'

    // ok, because no more than 1 empty lines before
    public static class Class3 { }


    // violation ''//' has more than 1 empty lines before.'
    public static class Class4 { }


    // violation ''//' has more than 1 empty lines before.'
    public
    // ok, because no more than 1 empty lines before
    static class Class5 { }


    // violation ''//' has more than 1 empty lines before.'
    public
    /* something */
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

        }
    }

    // ok, because no more than 1 empty lines before
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

    // ok, because no more than 1 empty lines before

    // ok, because no more than 1 empty lines before
    // .
    // .
    // .
    interface Interface5 { }


    // violation ''//' has more than 1 empty lines before.'
    public enum Enum1 {
        E1, E2
    }

    // ok, because no more than 1 empty lines before
    public enum Enum2 { }


    // violation ''//' has more than 1 empty lines before.'
    // ok, because no more than 1 empty lines before

    // ok, because no more than 1 empty lines before
    public enum Enum3 { }
    // ok, because no more than 1 empty lines before

    public enum Enum4 { }
    // ok, because no more than 1 empty lines before

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

    // ok, because no more than 1 empty lines before


    // violation ''//' has more than 1 empty lines before.'
    {
       abs(1);
    }

    /* something */
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

    // ok, because no more than 1 empty lines before
    /* . */ public InputEmptyLineSeparatorWithComments(int i) {
        testNoViolationWithJavadoc = 1;
    }

    // ok, because no more than 1 empty lines before
    // ok, because no more than 1 empty lines before

    // ok, because no more than 1 empty lines before
    public InputEmptyLineSeparatorWithComments(int i, int j) {
        testNoViolationWithJavadoc = 1;
    }


    // violation ''//' has more than 1 empty lines before.'
    // ok, because no more than 1 empty lines before

    // ok, because no more than 1 empty lines before




    // violation ''//' has more than 1 empty lines before.'
    public InputEmptyLineSeparatorWithComments(int i, int j, int k) {
        testNoViolationWithJavadoc = 1;
    }

}
