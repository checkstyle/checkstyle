/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator; // violation ''package' should be separated from previous line.'



// warn



import java.lang.Object;  

import java.lang.Class; // no violation


 
import java.lang.Integer;

// no violation
import java.lang.Long;


/*
 * violation
 */
import java.lang.Float;

/*
 * no violation
 */
import java.lang.Double;


/**
 * violation
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


 
// .
import java.lang.Boolean;
// no violation
import java.lang.Byte;

// no violation
/* no violation */
import java.lang.Short;


/* violation */
// .
import java.lang.Number;


import java.lang.Runnable;
import java.lang.Thread;

// no violation


 
import java.lang.StringBuilder;


/*
 * violation
 */
import static java.lang.Math.abs;

public class InputEmptyLineSeparatorWithComments2 {


    public int testViolationWithoutComment = 1;

    public int testNoViolationWithoutComment = 2; // no violation


    // Should have violation
    public int testViolationWithSingleLineComment = 3;

    // Should not have violations
    public int testNoViolationWithSingleLineComment = 4;


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


    public void testViolationWithoutComment() {
    }  

    public void testNoViolationWithoutComment() {
    } // no violation


    // Should have violation
    public void testViolationWithSingleLineComment() {
    }

    // Should not have
    // a violation
    public void testNoViolationWithSingleLineComment() {
    }


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

    public static class Class1 { } // no violation


    public static class Class2 { }  

    // no violation
    public static class Class3 { }


     
    public static class Class4 { }


     
    public
    // no violation
    static class Class5 { }


     
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
    public static class Class9 { }


    /**
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


     
    public interface Interface2 { }
     
    public // violation ''INTERFACE_DEF' should be separated from previous line.'
    // .
    interface Interface3 { }


    /* violation */
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


     
    public enum Enum1 {
        E1, E2
    }

    // no violation
    public enum Enum2 { }


     
    // no violation

    // no violation
    public enum Enum3 { }
    // no violation

    public enum Enum4 { }
    // no violation

    public enum Enum5 { }


     

    public


     
    static

    enum Enum6 { }


     
    static {
        abs(2);
    }

    // no violation


     
    {
       abs(1);
    }

    /* no violation */
    { }


     
    {
        int i = 1;
    }


     
    // .
    /* . */ public InputEmptyLineSeparatorWithComments2() {
        testNoViolationWithJavadoc = 1;
    }

    // no violation
    /* . */ public InputEmptyLineSeparatorWithComments2(int i) {
        testNoViolationWithJavadoc = 1;
    }

    // no violation
    // no violation

    // no violation
    public InputEmptyLineSeparatorWithComments2(int i, int j) {
        testNoViolationWithJavadoc = 1;
    }


     
    // no violation

    // no violation




     
    public InputEmptyLineSeparatorWithComments2(int i, int j, int k) {
        testNoViolationWithJavadoc = 1;
    }

}
