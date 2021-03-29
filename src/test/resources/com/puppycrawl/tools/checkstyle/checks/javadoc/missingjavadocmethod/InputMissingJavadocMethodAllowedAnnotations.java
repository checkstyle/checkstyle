package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/**
 * Some javadoc.
 */
public class InputMissingJavadocMethodAllowedAnnotations implements SomeInterface {

    @ThisIsOk
    public void allowed1() {}

    @ThisIsOkToo
    public void allowed2() {}

    @com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod.ThisIsOk
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
