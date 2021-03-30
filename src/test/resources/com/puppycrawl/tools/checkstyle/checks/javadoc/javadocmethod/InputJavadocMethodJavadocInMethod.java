package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

/**
 * Config: default
 */
public class InputJavadocMethodJavadocInMethod {
    public void foo1() { } // ok

    @Deprecated
    public void foo2() { } // ok

    @Deprecated
    /** */
    public void foo3() { } // ok

    public void foo4() { /** */ } // ok

    @Deprecated
    public void foo5() { /** */ } // ok

    @Deprecated
    /** */
    public void foo6() { /** */ } // ok

    /** */
    public void foo7() { /** */ } // ok

    /** */
    @Deprecated
    public void foo8() { /** */ } // ok

    /** */
    @Deprecated
    /** */
    public void foo9() { /** */ } // ok
}
