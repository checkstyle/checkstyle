/*
JavadocStyle
checkEmptyJavadoc = (default)false
checkFirstSentence = (default)true
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
excludeScope = (default)null
scope = (default)private
violateExecutionOnNonTightHtml = (default)false
tokens = VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.utils.javadocutil;

class InputJavadocUtilVariableDefComments {
    // violation below 'First sentence should end with a period.'
    /**field*/
    int field;

    // violation below 'First sentence should end with a period.'
    /**modifier*/
    private int modifierPath;

    // violation below 'First sentence should end with a period.'
    /**annotation*/
    @Deprecated
    String annotationPath;

    int noJavadoc;

    /**dangling*/

    // violation below 'First sentence should end with a period.'
    /**real*/
    String danglingReal;

    Object initializerCommentOnly = new Object() {
        /**nope*/
        class Nested {
        }
    };
}
