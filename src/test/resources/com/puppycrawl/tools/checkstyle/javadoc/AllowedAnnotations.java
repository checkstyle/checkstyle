package com.puppycrawl.tools.checkstyle.javadoc;

/**
 * Some javadoc.
 */
public class AllowedAnnotations implements SomeInterface {

    @ThisIsOk
    public void allowed1() {}

    @ThisIsOkToo
    public void allowed2() {}

    @com.puppycrawl.tools.checkstyle.javadoc.ThisIsOk
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
