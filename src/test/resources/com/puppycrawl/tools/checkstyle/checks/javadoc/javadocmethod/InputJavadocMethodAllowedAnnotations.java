/*
JavadocMethod
allowedAnnotations = Override,ThisIsOk, \t\n\t ThisIsOkToo
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodAllowedAnnotations implements SomeInterface {

    @ThisIsOk
    public void allowed1() {} // ok

    @ThisIsOkToo
    public void allowed2() {} // ok

    @com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod.ThisIsOk
    public void allowed3() {} // ok

    @Override
    public void method() {} // ok
}

/**
 * Documented.
 */
interface SomeInterface { // ok
    /**
     * Documented.
     */
    void method(); // ok
}

/**
 * Some javadoc.
 */
@interface ThisIsOk {} // ok

/**
 * Some javadoc.
 */
@interface ThisIsOkToo {} // ok
