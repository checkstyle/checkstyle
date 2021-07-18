/*
JavadocStyle
scope = (default)private
excludeScope = (default)null
checkFirstSentence = false
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
checkEmptyJavadoc = (default)false
checkHtml = (default)true
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleHtmlComment // ok
{
    /**
     * sometimes a tag starts
     * <pre>
     * somewhere and has a comment in the middle
     * <!-- ignore this -->
     * and ends afterwards
     * </pre>
     */
    private void method1() {} // ok

    /**
     * sometimes a tag starts
     * <pre>
     * somewhere and has a multiline comment in the middle
     * <!-- ignore this
     * spanning
     * multiple lines -->
     * and ends afterwards
     * </pre>
     */
    private void method2() {} // ok

    /**
     * sometimes a tag starts
     * <pre>
     * somewhere and has a multiline comment in the middle
     * <!-- ignore this
     * spanning
     * multiple lines --></pre>
     * and ends on the same line
     */
    private void method3() {} // ok

    /**
     * sometimes a tag starts
     * <pre>
     * somewhere and has a comment in the middle
     * <!-- ignore this --> with text following
     * and ends afterwards
     * </pre>
     */
    private void method4() {} // ok

    /**
     * sometimes a tag starts
     * <pre><!-- ignore this --></pre>
     * and ends with a comment in the middle
     */
    private void method5() {} // ok

    /**
     * Parameterized interface.
     * @param <I>
     */
    interface Interface<I> {} // ok
}
