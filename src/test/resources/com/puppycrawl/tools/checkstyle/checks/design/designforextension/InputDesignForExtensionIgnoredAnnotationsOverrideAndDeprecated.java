/*
DesignForExtension
ignoredAnnotations = Override, Deprecated, MyAnnotation
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public class InputDesignForExtensionIgnoredAnnotationsOverrideAndDeprecated {

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Javadoc.
     * @param obj object.
     * @return boolean.
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Deprecated
    public void testFoo() throws Exception {
        final int a = 5;
        final int b = 6;
    }

    @Deprecated
    public String toString() {
        return super.toString();
    }

    // violation below ''foo1' does not have javadoc that explains how to do that safely'
    public int foo1() {return  1;}

    /**
     *
     * @return
     */
    public int foo2() {return 2;}

    public void foo3() {}

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
}
