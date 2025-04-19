/*
JavadocVariable

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
