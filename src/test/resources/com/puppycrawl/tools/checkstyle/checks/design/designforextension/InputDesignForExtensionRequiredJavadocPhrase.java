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

    /** This method can safely be overridden. */
    public int foo5(int a, int b) {return a + b;} // violation 'Class 'InputDesignForExtensionRequiredJavadocPhrase' looks like designed for extension (can be subclassed), but the method 'foo5' does not have javadoc that explains how to do that safely. If class is not designed for extension consider making the class 'InputDesignForExtensionRequiredJavadocPhrase' final or making the method 'foo5' static/final/abstract/empty, or adding allowed annotation for the method.'

    public final int foo6(int a) {return a - 2;} // ok, final

    protected final int foo7(int a) {return a - 2;} // ok, final

    /** */
    public int foo8(int a) {return a - 2;} // violation 'Class 'InputDesignForExtensionRequiredJavadocPhrase' looks like designed for extension (can be subclassed), but the method 'foo8' does not have javadoc that explains how to do that safely. If class is not designed for extension consider making the class 'InputDesignForExtensionRequiredJavadocPhrase' final or making the method 'foo8' static/final/abstract/empty, or adding allowed annotation for the method.'

    // This implementation
    public int foo9(int a, int b) {return a + b;} // violation 'Class 'InputDesignForExtensionRequiredJavadocPhrase' looks like designed for extension (can be subclassed), but the method 'foo9' does not have javadoc that explains how to do that safely. If class is not designed for extension consider making the class 'InputDesignForExtensionRequiredJavadocPhrase' final or making the method 'foo9' static/final/abstract/empty, or adding allowed annotation for the method.'

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
    public int foo12(int a, int b) {  // violation 'Class 'InputDesignForExtensionRequiredJavadocPhrase' looks like designed for extension (can be subclassed), but the method 'foo12' does not have javadoc that explains how to do that safely. If class is not designed for extension consider making the class 'InputDesignForExtensionRequiredJavadocPhrase' final or making the method 'foo12' static/final/abstract/empty, or adding allowed annotation for the method.'
        return a + b;
    }
}
