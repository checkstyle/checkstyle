/*
com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocVariableCheck
accessModifiers = (default)public,protected,package,private
ignoreNamePattern = (default)null
tokens = (default)VARIABLE_DEF, ENUM_CONSTANT_DEF

*/

package com.puppycrawl.tools.checkstyle.utils.javadocutil;

enum InputJavadocUtilEnumConstantDefComments {

    /**constant*/
    FIRST,

    /**annotation*/
    @Deprecated
    ANNOTATED,

    // violation below 'Missing a Javadoc comment'
    BODY {
        /**nope*/
        void method() {
        }
    },

    // violation below 'Missing a Javadoc comment'
    NO_JAVADOC,

    // violation below 'Missing a Javadoc comment'
    REAL;
}
