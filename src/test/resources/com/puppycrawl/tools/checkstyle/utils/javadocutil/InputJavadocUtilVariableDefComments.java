/*
com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocVariableCheck
accessModifiers = (default)public,protected,package,private
ignoreNamePattern = (default)null
tokens = VARIABLE_DEF

*/

package com.puppycrawl.tools.checkstyle.utils.javadocutil;

class InputJavadocUtilVariableDefComments {

    /**field*/
    int field;

    // violation below 'Missing a Javadoc comment'
    private int modifierPath;

    // violation below 'Missing a Javadoc comment'
    @Deprecated
    String annotationPath;

    // violation below 'Missing a Javadoc comment'
    int noJavadoc;

    /**dangling*/

    /**real*/
    String danglingReal;

    // violation below 'Missing a Javadoc comment'
    Object initializerCommentOnly = new Object() {
        /**nope*/
        class Nested {
        }
    };
}
