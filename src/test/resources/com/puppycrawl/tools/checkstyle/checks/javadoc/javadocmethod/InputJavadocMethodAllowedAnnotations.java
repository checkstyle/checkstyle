package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

/**
 * Some javadoc.
 */
public class InputJavadocMethodAllowedAnnotations implements SomeInterface {

    @ThisIsOk
    public void allowed1() {}

    @ThisIsOkToo
    public void allowed2() {}

    @com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod.ThisIsOk
    public void allowed3() {}

    @Override
    public void method() {}
}

/**
 * Documented.
 */
interface SomeInterface {
    /**
     * Documented.
     */
    void method();
}

/**
 * Some javadoc.
 */
@interface ThisIsOk {}

/**
 * Some javadoc.
 */
@interface ThisIsOkToo {}
