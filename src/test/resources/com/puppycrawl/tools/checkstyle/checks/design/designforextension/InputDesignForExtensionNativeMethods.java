/*
DesignForExtension
ignoredAnnotations = Deprecated
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public class InputDesignForExtensionNativeMethods {

    // violation below 'Class 'InputDesignForExtensionNativeMethods'
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
    // violation below 'Class 'InputDesignForExtensionNativeMethods'
    public native void foo6();

    @Deprecated
    public native void foo7();
}
