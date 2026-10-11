/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = This implementation


*/

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
    public int foo1(int a, int b) {return a + b;}  // ok, required comment pattern in javadoc

    /**
     * This implementation is required for ...
     *
     * @param a
     * @param b
     * @return sum
     */
    public int foo2(int a, int b) {return a + b;}  // ok, required comment pattern in javadoc

    /** This implementation is for ... */
    public int foo3(int a, int b) {return a + b;}  // ok, required comment pattern in javadoc

    /**
     * This implementation ...
     */
    public int foo4(int a, int b) {return a + b;}  // ok, required comment pattern in javadoc

    // violation 2 lines below ''foo5' does not have javadoc that explains'
    /** This method can safely be overridden. */
    public int foo5(int a, int b) {return a + b;}

    public final int foo6(int a) {return a - 2;} // ok, final

    protected final int foo7(int a) {return a - 2;} // ok, final

    // violation 2 lines below ''foo8' does not have javadoc that explains'
    /** */
    public int foo8(int a) {return a - 2;}

    // This implementation
    // violation below 'method 'foo9' does not have javadoc that explains how to do that safely'
    public int foo9(int a, int b) {return a + b;}

    @Deprecated
    protected final int foo10(int a) {return a - 2;} // ok, deprecated

    /**
     * This implementation is for <p> some html code
     * </p>.
     *
     * @param a
     * @param b
     * @return sum
     */
    public int foo11(int a, int b) {return a + b;} // ok, required comment pattern in javadoc

    // violation 2 lines below ''foo12' does not have javadoc that explains'
    /**This method can safely be overridden. */
    public int foo12(int a, int b) {
        return a + b;
    }
}
