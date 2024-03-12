/*
JavadocStyle
scope = public
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

public interface InputJavadocStyleInterfaceMemberScopeIsPublic {

    // violation below 'Javadoc has empty description section'
    /** */
    enum Enum {

        // violation below 'Javadoc has empty description section'
        /** */
        CONSTANT(0);

        /** */
        final int value;

        /** */
        Enum(int value) {
            this.value = value;
        }

        /** */
        void method() {
        }

    }

}
