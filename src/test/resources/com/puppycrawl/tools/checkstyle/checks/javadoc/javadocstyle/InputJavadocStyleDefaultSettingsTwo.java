/*
JavadocStyle
scope = (default)private
excludeScope = (default)null
checkFirstSentence = (default)true
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
checkEmptyJavadoc = (default)false
checkHtml = (default)true
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleDefaultSettingsTwo
{
    // violation 6 lines below  'Extra HTML tag found: </img>'
    /**
     * Real men don't use XHTML.
     * <br />
     * <hr/>
     * < br/>
     * <img src="schattenparker.jpg"/></img>
     */
    private void method12() {}

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
    private void method13() {}

    // violation 3 lines below 'Unclosed HTML tag found: <blockquote>'
    /**
     * Some problematic javadoc. Sample usage:
     * <blockquote>
     */
    private void method14() {}

    // violation below 'First sentence should end with a period.'
    /**
     * Empty line between javadoc and method declaration cause wrong
     * line number for reporting error (bug 841942)
     */

    private void method15() {}

    /** Description of field: {@value}. */
    public static final int dummy = 4911;

    /**
     */
    public void method16() {}

    /**
     * @param a A parameter
     */
    protected void method17(String a) {}

    /**
     * @exception RuntimeException should be thrown
     */
    void method18(String a) {}
    /**
     */
    private static int ASDF = 0;

    /** @see java.lang.Object */
    public void method19() {}

    // violation 5 lines below 'Extra HTML tag found: </string>'
    /**
     * Checks HTML tags in javadoc.
     *
     * HTML no good tag
     * <string>Tests</string>
     *
     */
    public void method20() {}

    /** Set of all class field names.*/
    public String field;
}
