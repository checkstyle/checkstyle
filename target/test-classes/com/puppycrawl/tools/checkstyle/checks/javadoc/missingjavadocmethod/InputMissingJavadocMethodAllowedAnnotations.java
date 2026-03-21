/*
MissingJavadocMethod
minLineCount = (default)-1
allowedAnnotations = Override,ThisIsOk, \t\n\t ThisIsOkToo
scope = private
excludeScope = (default)null
allowMissingPropertyJavadoc = (default)false
ignoreMethodNamesRegex = (default)null
tokens = (default)METHOD_DEF , CTOR_DEF , ANNOTATION_FIELD_DEF , COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/* Config:
 * allowedAnnotations = "Override, ThisIsOk, \t\n\t ThisIsOkToo"
 * scope = "private"
 */
/**
 * Some javadoc.
 */
public class InputMissingJavadocMethodAllowedAnnotations implements SomeInterface {

    @ThisIsOk
    public void allowed1() {}

    @ThisIsOkToo
    public void allowed2() {}

    // violation below 'Missing a Javadoc comment.'
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
