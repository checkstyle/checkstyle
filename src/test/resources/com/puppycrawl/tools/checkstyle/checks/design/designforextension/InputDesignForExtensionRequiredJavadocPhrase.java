package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public class InputDesignForExtensionRequiredJavadocPhrase {

    /**
     * This implementation is for <p> some html code
     * </p>.
     *
     * @param a
     * @param b
     * @return sum
     */
    public int foo1(int a, int b) {return a + b;}

    /**
     * This implementation is required for ...
     *
     * @param a
     * @param b
     * @return sum
     */
    public int foo2(int a, int b) {return a + b;}

    /** This implementation is for ... */
    public int foo3(int a, int b) {return a + b;}

    /**
     * Not This implementation
     */
    public int foo4(int a, int b) {return a + b;}

    /** This method can safely be overridden. */
    public int foo5(int a, int b) {return a + b;}

    public final int foo6(int a) {return a - 2;}

    protected final int foo7(int a) {return a - 2;}

    /** */
    public int foo8(int a) {return a - 2;}

    // This implementation
    public int foo9(int a, int b) {return a + b;}

    @Deprecated
    protected final int foo10(int a) {return a - 2;}

    /**
     * This
     *          implementation is for <p> some html code
     * </p>.
     *
     * @param a
     * @param b
     * @return sum
     */
    public int foo11(int a, int b) {return a + b;}
}

