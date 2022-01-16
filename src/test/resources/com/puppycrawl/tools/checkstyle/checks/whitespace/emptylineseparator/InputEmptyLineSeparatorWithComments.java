/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator; // violation



// violation



import java.lang.Object; // violation

import java.lang.Class; // no violation


// violation
import java.lang.Integer;

// no violation
import java.lang.Long;


/*
 * // violation above
 */
import java.lang.Float;

/*
 * no violation
 */
import java.lang.Double;


/**
 * // violation above
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


// violation
// .
import java.lang.Boolean;
// no violation
import java.lang.Byte;

// no violation
/* no violation */
import java.lang.Short;


/* violation */
// violation above
import java.lang.Number;


import java.lang.Runnable; // violation
import java.lang.Thread;

// no violation


// violation
import java.lang.StringBuilder;


/*
 * // violation above
 */
import static java.lang.Math.abs;

public class InputEmptyLineSeparatorWithComments {


    public int testViolationWithoutComment = 1; // violation

    public int testNoViolationWithoutComment = 2; // no violation


    // violation
    public int testViolationWithSingleLineComment = 3;

    // Should not have violations
    public int testNoViolationWithSingleLineComment = 4;


    /*
     * Should have // violation above
     * violation
     */
    public int testViolationWithMultilineComment = 5;

    /*
     * Should not have
     * violation
     */
    public int testNoViolationWithMultilineComment = 6;


    /**
     * Should have // violation above
     * violation
     */
    public int testViolationWithJavadoc = 7;

    /**
     * Should not have
     * violation
     */
    public int testNoViolationWithJavadoc = 8;


    public void testViolationWithoutComment() {
    } // violation above

    public void testNoViolationWithoutComment() {
    } // no violation


    // violation
    public void testViolationWithSingleLineComment() {
    }

    // Should not have
    // a violation
    public void testNoViolationWithSingleLineComment() {
    }


    /*
     * Should have // violation above
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


    /**
     * Should have // violation above
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


    public static class Class2 { } // violation

    // no violation
    public static class Class3 { }


    // violation
    public static class Class4 { }


    // violation
    public
    // no violation
    static class Class5 { }


    // violation
    public
    /* no violation */
    static class Class6 { }

    /*
     * Should not have
     * violation
     */
    public static class Class7 { }


    /*
     * Should have // violation above
     * violation
     */
    public static class Class8 { }

    /**
     * Should not have
     * violation
     */
    public static class Class9 { }


    /**
     * Should have // violation above
     * violation
     */
    public static class Class10 {
        {
            // no violation
        }
    }

    // no violation
    public interface Interface1 { }


    // violation
    public interface Interface2 { }
    // violation below
    public
    // .
    interface Interface3 { }


    /* // violation
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


    // violation
    public enum Enum1 {
        E1, E2
    }

    // no violation
    public enum Enum2 { }


    // violation
    // no violation

    // no violation
    public enum Enum3 { }
    // no violation

    public enum Enum4 { }
    // no violation

    public enum Enum5 { }


    // violation

    public


    // violation
    static

    enum Enum6 { }


    // violation
    static {
        abs(2);
    }

    // no violation


    // violation
    {
       abs(1);
    }

    /* no violation */
    { }


    // violation
    {
        int i = 1;
    }


    // violation
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


    // violation
    // no violation

    // no violation




    // violation
    public InputEmptyLineSeparatorWithComments(int i, int j, int k) {
        testNoViolationWithJavadoc = 1;
    }

}
