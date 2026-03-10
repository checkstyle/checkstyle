/*
JavadocStyle
format = traditional
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

/**
 * Test for format=traditional.
 * Only traditional Javadoc comments (starting with /**) are checked.
 */
public class InputJavadocStyleFormatTraditional {

    // violation below 'First sentence should end with a period'
    /**
     * Traditional Javadoc without period
     */
    public void methodWithTraditionalJavadoc() {}

    /**
     * Traditional Javadoc with period.
     */
    public void methodWithValidTraditionalJavadoc() {}
}
