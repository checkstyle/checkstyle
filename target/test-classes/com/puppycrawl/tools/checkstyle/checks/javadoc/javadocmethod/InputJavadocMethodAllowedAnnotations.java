/*
JavadocMethod
allowedAnnotations = Override,ThisIsOk, \t\n\t ThisIsOkToo
validateThrows = (default)false
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

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
