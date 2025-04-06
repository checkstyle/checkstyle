/*
JavadocStyle
endOfSentenceFormat = \n
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

public class InputJavadocStyleCheck5 {

    /**             Set of all class field names.        */
    public String field; // violation above 'First sentence should end with a period'
}
