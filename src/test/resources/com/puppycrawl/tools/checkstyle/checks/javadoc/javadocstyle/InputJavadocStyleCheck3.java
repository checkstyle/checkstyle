/*
JavadocStyle
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
scope = (default)private
excludeScope = (default)null
checkFirstSentence = (default)true
checkEmptyJavadoc = (default)false
checkHtml = (default)true
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleCheck3 {
    // violation below 'First sentence should end with a period'
    /**
     * check：../
     */
    public static final String SPOT_SINGLE_SLASH = "../";
}
