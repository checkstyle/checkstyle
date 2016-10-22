package com.puppycrawl.tools.checkstyle.checks.design;

public class InputDesignForExtensionIgnoredAnnotations {

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

    public int foo1() {return  1;} // violation

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

    @Deprecated
    /** */
    public void foo15() { /** */ }

    @Deprecated
    // comment
    public void foo16() { }

    @Deprecated
    @InputLocalAnnotations.ClassRule
    public void foo17() { return; }

    @Deprecated
    @InputLocalAnnotations.ClassRule
    /** */
    public void foo18() { return; }

    @Deprecated
    /** */
    @InputLocalAnnotations.ClassRule
    public void foo19() { return; }

    /** */
    @Deprecated
    @InputLocalAnnotations.ClassRule
    public void foo20() { return; }

    @InputLocalAnnotations.ClassRule
    public void foo21() { return; } // violation
}
