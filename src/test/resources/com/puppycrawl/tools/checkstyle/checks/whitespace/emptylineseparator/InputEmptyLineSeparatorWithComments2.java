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

import java.lang.Class;



import java.lang.Integer;


import java.lang.Long;


/*
 * javadoc
 */
import java.lang.Float;

/*
 * javadoc
 */
import java.lang.Double;


/**
 * javadoc
 */
import java.lang.Character;

/**
 * javadoc
 */
import java.lang.String;

/*
 * javadoc
 */
/* javadoc */
import java.lang.Object;



// .
import java.lang.Boolean;

import java.lang.Byte;


/* javadoc */
import java.lang.Short;


/* ok javadoc */
// .
import java.lang.Number;


import java.lang.Runnable;
import java.lang.Thread;





import java.lang.StringBuilder;


/*
 * javadoc
 */
import static java.lang.Math.abs;

public class InputEmptyLineSeparatorWithComments2 {


    public int testViolationWithoutComment = 1;

    public int testNoViolationWithoutComment = 2;


    // Should have violation
    public int testViolationWithSingleLineComment = 3;

    // Should not have violations
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

    public static class Class1 { }


    public static class Class2 { }


    public static class Class3 { }



    public static class Class4 { }



    public

    static class Class5 { }



    public
    /* javadoc  */
    static class Class6 { }

    /*
     * Should not have
     * something
     */
    public static class Class7 { }


    /*
     * Should have
     * something
     */
    public static class Class8 { }

    /**
     * Should not have
     * something
     */
    public static class Class9 { }


    /**
     * Should have
     * something
     */
    public static class Class10 {
        {

        }
    }


    public interface Interface1 { }



    public interface Interface2 { }
    // some comment
    public // violation ''INTERFACE_DEF' should be separated from previous line.'
    // .
    interface Interface3 { }


    /* something */
    /* . */
    /* . */
    /* . */
    interface Interface4 { }




    // .
    // .
    // .
    interface Interface5 { }



    public enum Enum1 {
        E1, E2
    }


    public enum Enum2 { }






    public enum Enum3 { }


    public enum Enum4 { }


    public enum Enum5 { }




    public



    static

    enum Enum6 { }



    static {
        abs(2);
    }





    {
       abs(1);
    }

    /* something */
    { }



    {
        int i = 1;
    }



    // .
    /* . */ public InputEmptyLineSeparatorWithComments2() {
        testNoViolationWithJavadoc = 1;
    }


    /* . */ public InputEmptyLineSeparatorWithComments2(int i) {
        testNoViolationWithJavadoc = 1;
    }





    public InputEmptyLineSeparatorWithComments2(int i, int j) {
        testNoViolationWithJavadoc = 1;
    }











    public InputEmptyLineSeparatorWithComments2(int i, int j, int k) {
        testNoViolationWithJavadoc = 1;
    }

}
