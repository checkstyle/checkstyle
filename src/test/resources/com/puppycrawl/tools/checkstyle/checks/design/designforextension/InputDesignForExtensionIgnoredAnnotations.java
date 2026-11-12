/*
DesignForExtension
ignoredAnnotations = Override, Deprecated, MyAnnotation
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

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

    // violation below ''foo1' does not have javadoc that explains how to do that safely'
    public int foo1() {return  1;}

    /**
     *
     * @return
     */
    public int foo2() {return 2;}

    public void foo3() {}

}
