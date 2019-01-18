package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

import java.lang.Object; // no violation

import java.lang.Class; // no violation

// no violation
import java.lang.Long;

/*
 * no violation
 */
import java.lang.Double;

/*
 * no violation
 */
/* no violation */
import java.lang.Object;

// .
// no violation
import java.lang.Boolean;
// no violation
import java.lang.Byte;

// no violation
/* no violation */
import java.lang.Short;
import java.lang.Number;
import java.lang.Runnable;
import java.lang.Thread;
import java.lang.StringBuilder;

public class InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeComments {

    public int testNoViolation1 = 1; // violation

    public int testNoViolation2 = 2; // no violation

    // Should
    // not
    // have
    // violations
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

    public void testNoViolationMethod1() {
    } // no violation

    public void testNoViolationMethod2() {
    } // no violation

    // Should not have
    // violation
    public void testNoViolationMethod3() {


        // no violation

    }

    /* no violation */

    /*
     * Should not have
     * violation
     */
    public void testNoViolationMethod4() {
        // no violation
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

    // no violation

    // no violation
    // no violation

    /**
     * Should have
     * violation
     */
    public void testNoViolationWithJavadoc1() {


        // no violation



        /* no violtaion */



        // no violation

    }

    /**
     * Should not have
     * violation
     */
    public void testNoViolationWithJavadoc2() { // no violation
    }

    public static class Class1 { } // no violation


    public static class Class2 { } // violation

    // no violation

    public static class Class3 {


        // no violation



        /* no violation */


        // no violation

    }

    // no violation

    // no violation

    public static class Class4 {




        /*
         * no
         * violation
         */


    }

    /* no violation */
    public
    // no violation
    static class Class5 {


        // no violation

    }

    public
    /* no violation */

    // no violation
    static class Class6 { }

    /*
     * Should not have
     * violation
     */
    public static class Class7 { }

    // no violation

    // no violation

    // no violation

    /*
     * Should have
     * violation
     */
    public static class Class8 { }

    /**
     * Should not have
     * violation
     */
    public static class Class9 {
        {
            // no violation
        }
    }

    // no violation
    public interface Interface1 {


        // no violation



        /* no violtaion */



        // no violation


    }

    /** no violation */
    public interface Interface2 { }
    // no violation

    public
    // .
    interface Interface3 { }

    /* no violation */
    /* . */
    /* . */
    /* . */
    interface Interface4 {


        // no violation

    }

    // no violation

    // no violation
    // .
    // .
    // .
    interface Interface5 {




        /*
         * no
         * violation
         */


    }

    // no violation
    public enum Enum1 {
        E1, E2
    }

    /*
     * no
     * violation.
     */

    // no violation
    public enum Enum2 {


        // no violation



        /* no violation */



        // no violation


    }

    // no violation
    // no violation

    // no violation
    public enum Enum3 {


        // no violation

    }
    // no violation

    public enum Enum4 {
        /*
         * no
         * violation
         */
    }
    // no violation

    public enum Enum5 { /* no violation */ }

    // no violation

    public

    // violation
    static

    enum Enum6 { }

    // no violation
    static {
        Math.abs(2);
    }

    // no violation

    // no violation
    {
        Math.abs(1);


        // no violation

    }

    /* no violation */
    {

        /*
         * no
         * violation
         */


    }

    /* no violation */

    // violation
    {
        int i = 1;
    }

    // no violation
    // .
    /* . */ public InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeComments() {
        testNoViolationWithJavadoc = 1;


        //no violation



        /*
         * no
         * violation
         */


        // no
        // violation
    }

    // no violation
    /* . */ public InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeComments(int i) {
        testNoViolationWithJavadoc = 1;
        // no violation
    }

    // no violation
    // no violation

    // no violation
    public InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeComments(int i, int j) {
        testNoViolationWithJavadoc = 1;
    }

    /* no violation */

    // no violation
    // no violation

    // no violation

    // no violation
    public InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeComments(int i, int j, int k) {
        testNoViolationWithJavadoc = 1;
    }




    // no violation

}



/* no violation */
