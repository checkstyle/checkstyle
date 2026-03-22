/*
JavadocType
scope = (default)private
excludeScope = (default)null
authorFormat = (default)null
versionFormat = \\S
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com . puppycrawl
    .tools.
    checkstyle.checks.javadoc.javadoctype;

/**
 * Class for testing javadoc issues.
 * violation missing author tag
 **/
class InputJavadocTypeWhitespace_1 // violation 'missing @version tag.'
{
    /** another check */
    void donBradman(Runnable aRun)
    {
        donBradman(new Runnable() {
            public void run() {
            }
        });

        final Runnable r = new Runnable() {
            public void run() {
            }
        };
    }

    /** bug 806243 (NoWhitespaceBeforeCheck violation for anonymous inner class) */
    void bug806243()
    {
        Object o = new InputJavadocTypeWhitespace_1() {
            private int j ;
        };
    }
}

/**
 * Bug 806242 (NoWhitespaceBeforeCheck violation with an interface).
 * @author o_sukhodolsky
 * @version 1.0
 */
interface IFoo_1
{
    void foo() ;
}

/**
 * Avoid Whitespace violations in for loop.
 * @author lkuehne
 * @version 1.0
 */
class SpecialCasesInForLoop_1
{
    public void myMethod() {
        new Thread() {
            public void run() {
            }
        }.start();
    }
}
