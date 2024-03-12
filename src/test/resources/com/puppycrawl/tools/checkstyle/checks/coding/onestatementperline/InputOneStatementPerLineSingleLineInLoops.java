/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

public class InputOneStatementPerLineSingleLineInLoops {
    /**
     * Dummy variable to work on.
     */
    private int one = 0;

    /**
     * Dummy variable to work on.
     */
    private int two = 0;

    /**
     * While theoretically being distributed over two lines, this is a sample
     * of 2 statements on one line.
     */
    public void doIllegal2() {
        one = 1
        ; two = 2; // violation
    }

    /**
     * The StringBuffer is a Java-API-class that permits smalltalk-style concatenation
     * on the <code>append</code>-method.
     */
    public void doStringBuffer() {
        StringBuffer sb = new StringBuffer();
        sb.append("test ");
        sb.append("test2 ").append("test3 ");
        appendToSpringBuffer(sb, "test4");
    }

    /**
     * indirect stringbuffer-method. Used only internally.
     * @param sb The stringbuffer we want to append something
     * @param text The text to append
     */
    private void appendToSpringBuffer(StringBuffer sb, String text) {
        sb.append(text);
    }

    /**
     * Two declaration statements on the same line are illegal.
     */
    int a; int b; // violation

    /**
     * Two declaration statements which are not on the same line
     * are legal.
     */
    int c;
    int d;

    /**
     * Two assignment (declaration) statements on the same line are illegal.
     */
    int e = 1; int f = 2; // violation

    /**
     * Two assignment (declaration) statements on the different lines
     * are legal.
     */
    int g = 1;
    int h = 2;

    /**
     * This method contains two increment statements
     * and two object creation statements on the same line.
     */
    private void foo() {
        //This is two assignment (declaration)
        //statements on different lines
        int var1 = 1;
        int var2 = 2;

        //Two increment statements on the same line are illegal.
        var1++; var2++; // violation

        //Two object creation statements on the same line are illegal.
        Object obj1 = new Object(); Object obj2 = new Object(); // violation
    }

}
