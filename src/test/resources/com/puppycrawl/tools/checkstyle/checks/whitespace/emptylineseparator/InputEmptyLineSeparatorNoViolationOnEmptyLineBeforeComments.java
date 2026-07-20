/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, STATIC_INIT, INSTANCE_INIT, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

import java.lang.Object;

import java.lang.Class;

// ok below
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
// ok below
import java.lang.Boolean;
// ok below
import java.lang.Byte;

// ok below
/* no violation */
import java.lang.Short;
import java.lang.Number;
import java.lang.Runnable;
import java.lang.Thread;
import java.lang.StringBuilder;

public class InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeComments {

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

    public static class Class1 { }


    public static class Class2 { }



    public static class Class3 {






        /* no violation */




    }





    public static class Class4 {




        /*
         * no
         * violation
         */


    }

    /* no violation */
    public

    static class Class5 {




    }

    public
    /* no violation */


    static class Class6 { }

    /*
     * Should not have
     * violation
     */
    public static class Class7 { }







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

        }
    }


    public interface Interface1 {






        /* no violtaion */






    }

    /** no violation */
    public interface Interface2 { }


    public
    // .
    interface Interface3 { }

    /* no violation */
    /* . */
    /* . */
    /* . */
    interface Interface4 {




    }




    // .
    // .
    // .
    interface Interface5 {




        /*
         * no
         * violation
         */


    }


    public enum Enum1 {
        E1, E2
    }

    /*
     * no
     * violation.
     */


    public enum Enum2 {






        /* no violation */






    }





    public enum Enum3 {




    }


    public enum Enum4 {
        /*
         * no
         * violation
         */
    }


    public enum Enum5 { /* no violation */ }



    public


    static

    enum Enum6 { }


    static {
        Math.abs(2);
    }




    {
        Math.abs(1);




    }

    /* no violation */
    {

        /*
         * no
         * violation
         */


    }

    /* no violation */


    {
        int i = 1;
    }


    // .
    /* . */ public InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeComments() {
        testNoViolationWithJavadoc = 1;






        /*
         * no
         * violation
         */


        // no

    }


    /* . */ public InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeComments(int i) {
        testNoViolationWithJavadoc = 1;

    }





    public InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeComments(int i, int j) {
        testNoViolationWithJavadoc = 1;
    }

    /* no violation */







    public InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeComments(int i, int j, int k) {
        testNoViolationWithJavadoc = 1;
    }






}



/* no violation */
