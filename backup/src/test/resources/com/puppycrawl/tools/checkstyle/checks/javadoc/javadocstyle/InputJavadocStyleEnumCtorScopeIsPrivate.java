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

public enum InputJavadocStyleEnumCtorScopeIsPrivate {

    /** */ // violation
    CONSTANT(0);

    /** */ // violation
    final int value;

    /** */ // ok
    InputJavadocStyleEnumCtorScopeIsPrivate(int value) {
        this.value = value;
    }

    /** */ // violation
    void method() {
    }

}
