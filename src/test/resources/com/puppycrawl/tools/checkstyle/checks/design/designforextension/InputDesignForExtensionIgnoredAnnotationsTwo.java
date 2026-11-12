/*
DesignForExtension
ignoredAnnotations = Override, Deprecated, MyAnnotation
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public class InputDesignForExtensionIgnoredAnnotationsTwo {
    public class C extends B {
        @Deprecated
        @Override
        public void testFoo() {
            super.testFoo();
        }
    }

    public class B {
        /** Test foo*/
        public void testFoo() {
            final int a = 6;
        }
    }

    // Deprecated
    @Deprecated
    public void foo4() {return;}

    /*
     * Deprecated
     */
    @Deprecated
    public void foo5() {return;}

    @java.lang.Deprecated
    public void foo6() {return;}

    // Single line comment
    @Deprecated
    public void foo7() {
        return;
    }

    // Single line comments
    // organized in a block
    @Deprecated
    public void foo8() {
        return;
    }

    /** Javadoc comment */
    @Deprecated
    public void foo9() {
        return;
    }

    /* Block comment */
    @Deprecated
    public void foo10() {
        return;
    }

    @Deprecated
    /** */
    public int foo11() {
        return 1;
    }

    @Deprecated
    /* */
    public int foo12() {
        return 1;
    }

    @Deprecated
    /* */
    public void foo13() { }

    @Deprecated
    /** */
    public void foo14() { }

}
