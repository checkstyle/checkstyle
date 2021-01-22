package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

/* Config:
 * requiredJavadocPhrase = "This implementation"
 *
 */
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

    /** This method can safely be overridden. */
    public int foo5(int a, int b) {return a + b;} // violation

    public final int foo6(int a) {return a - 2;} // ok, final

    protected final int foo7(int a) {return a - 2;} // ok, final

    /** */
    public int foo8(int a) {return a - 2;} // violation

    // This implementation
    public int foo9(int a, int b) {return a + b;} // violation, not javadoc

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

    /**This method can safely be overridden. */
    public int foo12(int a, int b) {  // violation
        return a + b;
    }
}
