/*
DesignForExtension
ignoredAnnotations = Deprecated
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public class InputDesignForExtensionNativeMethods {

    // violation below 'Method 'foo1' does not have javadoc that explains'
    public native void foo1();

    public static native void foo2();

    protected static native void foo3();

    protected static final native void foo4();

    /**
     * Javadoc for native method.
     */
    public native void foo5();

    /*
     * Violation. Block-commend doc for native method.
     */
    // violation below 'Method 'foo6' does not have javadoc that explains'
    public native void foo6();

    @Deprecated
    public native void foo7();
}
