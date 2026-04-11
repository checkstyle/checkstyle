/*
JavadocVariable
accessModifiers = (default)public,protected,package,private
ignoreNamePattern = (default)null
tokens = (default)ENUM_CONSTANT_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

public class InputJavadocVariableAboveComment {
    /**
     * A public variable
     */
    /*
       test comment
     */
    public int variablePublic;


    /**
     * A package variable
     */
    /* package */ int variablePackage;

    public int x;  // violation, 'Missing a Javadoc comment.'
}
