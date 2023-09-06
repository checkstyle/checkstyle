/*
JavadocStyle
scope = package
excludeScope = (default)null
checkFirstSentence = (default)true
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
checkEmptyJavadoc = true
checkHtml = (default)true
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleScopePackage2
{
    /**
     * First sentence.
     * <pre>
     * +--LITERAL_DO (do)
     *     |
     *     +--SLIST ({)
     *         |
     *         +--EXPR
     *             |
     *             +--ASSIGN (=)
     *                 |
     *                 +--IDENT (x)
     *                 +--METHOD_CALL (()
     *                     |
     *                     +--DOT (.)
     *                         |
     *                         +--IDENT (rand)
     *                         +--IDENT (nextInt)
     *                     +--ELIST
     *                         |
     *                         +--EXPR
     *                             |
     *                             +--NUM_INT (10)
     *                     +--RPAREN ())
     *         +--SEMI (;)
     *         +--RCURLY (})
     *     +--LPAREN (()
     *     +--EXPR
     *         |
     *         +--LT (<)
     *             |
     *             +--IDENT (x)
     *             +--NUM_INT (5)
     *     +--RPAREN ())
     *     +--SEMI (;)
     * </pre>
     */
    private void method13() {} // ok

    /**
     * Some problematic javadoc. Sample usage:
     * <blockquote>
     */

    private void method14() {} // ok

    /**
     * Empty line between javadoc and method declaration cause wrong
     * line number for reporting error (bug 841942)
     */

    private void method15() {} // ok

    /** Description of field: {@value}. */
    public static final int dummy = 4911; // ok
    // violation below
    /**
     */
    public void method16() {}
    // violation below
    /**
     * @param a A parameter
     */
    protected void method17(String a) {}
    // violation below
    /**
     * @exception RuntimeException should be thrown
     */
    void method18(String a) {}

    /**
     */
    private static int ASDF = 0; // ok

    /** @see Object */ // violation
    public void method19() {}

    public enum Test
    {
        /** // violation
         * Value 1 without a period
         */
        value1,

        /**
         * Value 2 with a period.
         */
        value2, // ok
    }
}
