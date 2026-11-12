/*
DesignForExtension
ignoredAnnotations = Override, Deprecated, MyAnnotation
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public class InputDesignForExtensionIgnoredAnnotationsThree {
    @Deprecated
    /** */
    public void foo15() { /** */ }

    @Deprecated
    // comment
    public void foo16() { }

    @Deprecated
    @InputDesignForExtensionsLocalAnnotations.ClassRule
    public void foo17() { return; }

    @Deprecated
    @InputDesignForExtensionsLocalAnnotations.ClassRule
    /** */
    public void foo18() { return; }

    @Deprecated
    /** */
    @InputDesignForExtensionsLocalAnnotations.ClassRule
    public void foo19() { return; }

    /** */
    @Deprecated
    @InputDesignForExtensionsLocalAnnotations.ClassRule
    public void foo20() { return; }

    // violation below ''foo21' does not have javadoc that explains how to do that safely'
    @InputDesignForExtensionsLocalAnnotations.ClassRule
    public void foo21() { return; }

    private int age;

    // violation below ''setAge' does not have javadoc that explains how to do that safely'
    @Inject
    public void setAge(int age) {
        this.age = age;
    }

    public @interface Inject { }

    public @MyAnnotation void foo22() {
        return;
    }

    @MyAnnotation public void foo23() {
        return;
    }

    // violation below ''foo24' does not have javadoc that explains how to do that safely'
    public void foo24(@MyAnnotation int a) {
        return;
    }

    // violation 4 lines below ''dontUse4' does not have javadoc that explains how to do that'
    /**
     * @deprecated
     */
    <T> T dontUse4() {
        return null;
    }

    public @interface MyAnnotation { }
}
class InputDesignForExtensionsLocalAnnotations {
    @interface Rule {}

    @interface ClassRule {}
}
