package com.puppycrawl.tools.checkstyle.checks.coding;

/*
    This class provides test input for OneStatementPerLineCheck with different
    types of multiline statements.
    A Java statement is the smallest unit that is a complete instruction.
    Statements must end with a semicolon.
    Statements generally contain expressions (expressions have a value).
    One of the simplest is the Assignment Statement.

    <variable> = <expression>;
 */
public class OneStatementPerLineCheckInput2 {

    /**
     * One multiline  assignment (declaration) statement
     * is legal.
     */
    int e = 1, f = 2,
        g = 5;

    /**
     * One multiline  assignment (declaration) statement
     * is legal.
     */
    int h = 1,
        i = 2,
        j = 5;

    /**
     * One multiline  assignment (declaration) statement
     * is legal.
     */
    int k = 1,
        l = 2,
        m = 5
        ;

    /**
     * Two multiline  assignment (declaration) statements
     * on the same line are illegal.
     */
    int o = 1, p = 2,
        r = 5; int t;

    /**
     * Two assignment (declaration) statement
     * which are not on the same line are legal.
     */
    int one = 1,
        three = 5;
    int two = 2;

    /**
     * Two statements on the same line
     * (they both are distributed over two lines)
     * are illegal.
     */
    int var1 = 5,
        var4 = 5; int var2 = 6,
        var3 = 5;

    /**
     * Two statements on the same line
     * (the second is distributed over two lines)
     * are illegal.
     */
    int var6 = 5; int var7 = 6,
        var8 = 5;

    /**
     * Two statements on the same line
     * (they both are distributed over two lines)
     * are illegal.
     */
    private void foo() {
        toString(

        );toString(

        );
    }

    /**
     * While theoretically being distributed over three lines, this is a sample
     * of 2 statements on one line.
     */
    int var9 = 1,
        var10 = 5
            ; int var11 = 2;

    /**
     * Multiline for loop statement is legal.
     */
    private void foo2() {
        for (int n = 0,
             k = 1;
             n < 5; n++,
                 k--) {

        }
    }

    /**
     * Multiline for loop statement is legal.
     */
    private void foo3() {
        for(int n = 0,
            k = 1
            ; n<5
            ;
            n++, k--) {}
    }

    /**
     * One statement inside multiline for loop block is legal.
     */
    private void foo4() {
        for(int n = 0,
            k = 1
            ; n<5
            ; ) { int a = 5,
        b = 2;}
    }

    /**
     * Two statements on the same lne
     * inside multiline for loop block are illegal.
     */
    private void foo5() {
        for(int n = 0,
            k = 1
            ; n<5
            ;
            n++, k--) { var1++; var2++; }
    }
}
