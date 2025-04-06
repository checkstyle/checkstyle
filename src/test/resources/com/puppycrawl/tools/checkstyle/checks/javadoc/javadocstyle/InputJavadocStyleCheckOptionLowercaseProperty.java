/*
JavadocStyle
scope = (default)private
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
excludeScope = (default)null
checkFirstSentence = false
checkEmptyJavadoc = (default)false
checkHtml = (default)true
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleCheckOptionLowercaseProperty {

    /**
     * <pre>
     * somewhere and has a comment in the middle
     * <!-- ignore this -->
     * and ends afterwards
     * </PRE>
     */
    private void toLowerCaseForSingleHTMLTag() {
    }

    /**
     * And This is ok.<BR>
     * <CODE>lowercase</CODE>
     */
    public void allowedTAGToLowerCase() {
    }
}
