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



import java.lang.Object; // ok

import java.lang.Class; // ok


// ok
import java.lang.Integer;

// ok
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


// ok
// .
import java.lang.Boolean;
// ok
import java.lang.Byte;

// ok
/* javadoc */
import java.lang.Short;


/* ok javadoc */
// .
import java.lang.Number;


import java.lang.Runnable;
import java.lang.Thread;

// ok


// ok
import java.lang.StringBuilder;


/*
 * javadoc
 */
import static java.lang.Math.abs;

public class InputEmptyLineSeparatorWithComments2 {


    public int testViolationWithoutComment = 1;

    public int testNoViolationWithoutComment = 2; // ok


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
    } // ok

    public void testNoViolationWithoutComment() {
    } // ok


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

    public static class Class1 { } // ok


    public static class Class2 { } // ok

    // ok
    public static class Class3 { }


    // ok
    public static class Class4 { }


    // ok
    public
    // ok
    static class Class5 { }


    // ok
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
            // ok
        }
    }

    // ok
    public interface Interface1 { }


    // ok
    public interface Interface2 { }
    // ok
    public // violation ''INTERFACE_DEF' should be separated from previous line.'
    // .
    interface Interface3 { }


    /* something */
    /* . */
    /* . */
    /* . */
    interface Interface4 { }

    // ok

    // ok
    // .
    // .
    // .
    interface Interface5 { }


    // ok
    public enum Enum1 {
        E1, E2
    }

    // ok
    public enum Enum2 { }


    // ok
    // ok

    // ok
    public enum Enum3 { }
    // ok

    public enum Enum4 { }
    // ok

    public enum Enum5 { }


    // ok

    public


    // ok
    static

    enum Enum6 { }


    // ok
    static {
        abs(2);
    }

    // ok


    // ok
    {
       abs(1);
    }

    /* something */
    { }


    // ok
    {
        int i = 1;
    }


    // ok
    // .
    /* . */ public InputEmptyLineSeparatorWithComments2() {
        testNoViolationWithJavadoc = 1;
    }

    // ok
    /* . */ public InputEmptyLineSeparatorWithComments2(int i) {
        testNoViolationWithJavadoc = 1;
    }

    // ok
    // ok

    // ok
    public InputEmptyLineSeparatorWithComments2(int i, int j) {
        testNoViolationWithJavadoc = 1;
    }


    // ok
    // ok

    // ok




    // ok
    public InputEmptyLineSeparatorWithComments2(int i, int j, int k) {
        testNoViolationWithJavadoc = 1;
    }

}
