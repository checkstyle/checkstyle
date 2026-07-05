/*
JavadocStyle
checkEmptyJavadoc = (default)false
checkFirstSentence = (default)true
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
excludeScope = (default)null
scope = (default)private
violateExecutionOnNonTightHtml = (default)false
tokens = ENUM_CONSTANT_DEF


*/

package com.puppycrawl.tools.checkstyle.utils.javadocutil;

enum InputJavadocUtilEnumConstantDefComments {
    // violation below 'First sentence should end with a period.'
    /**constant*/
    FIRST,

    // violation below 'First sentence should end with a period.'
    /**annotation*/
    @Deprecated
    ANNOTATED,

    BODY {
        /**nope*/
        void method() {
        }
    },

    NO_JAVADOC,

    /**dangling*/

    // violation below 'First sentence should end with a period.'
    /**real*/
    REAL;
}
