/*
JavadocVariable
accessModifiers = public
ignoreNamePattern = (default)null
tokens = (default)ENUM_CONSTANT_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

class InputJavadocVariablePackagePrivateClass {
    public static String str = ""; // violation, 'Missing a Javadoc comment'
    protected int value;
    int packageField;
    private String privateField;
}
