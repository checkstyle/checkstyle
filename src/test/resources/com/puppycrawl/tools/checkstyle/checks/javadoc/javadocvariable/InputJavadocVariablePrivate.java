/*
JavadocVariable
accessModifiers = private
ignoreNamePattern = (default)null
tokens = VARIABLE_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

class InputJavadocVariablePrivate {

    private int privateFieldNoJavadoc = 1;  // violation, 'Missing a Javadoc comment'

    /** documented */
    private int privateFieldWithJavadoc = 2;

    private static class NestedPrivate {
        private String secret = "hidden";  // violation, 'Missing a Javadoc comment'
    }
}
