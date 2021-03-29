package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodJavadocInMethod {
    public void foo1() { }

    @Deprecated
    public void foo2() { }

    @Deprecated
    /** */
    public void foo3() { }

    public void foo4() { /** */ }

    @Deprecated
    public void foo5() { /** */ }

    @Deprecated
    /** */
    public void foo6() { /** */ }

    /** */
    public void foo7() { /** */ }

    /** */
    @Deprecated
    public void foo8() { /** */ }

    /** */
    @Deprecated
    /** */
    public void foo9() { /** */ }
}
