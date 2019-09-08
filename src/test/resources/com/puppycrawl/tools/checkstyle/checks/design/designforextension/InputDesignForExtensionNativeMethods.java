package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public class InputDesignForExtensionNativeMethods {

    // has a potentially complex implementation in native code.
    // We can't check that, so to be safe DesignForExtension requires
    // native methods to also be final
    public native void foo1(); // violation

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
    public native void foo6();

    @Deprecated
    public native void foo7();
}
