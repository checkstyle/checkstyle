/*
JavadocVariable
accessModifiers = (default)public,protected,package,private
ignoreNamePattern = (default)null
tokens = ENUM_CONSTANT_DEF, VARIABLE_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

public class InputJavadocVariablePackagePrivate {

    /** Some javadoc */
    String packagePrivateVar = "test";

    public String publicVar = "test"; // violation, 'Missing a Javadoc comment'

    /** Some javadoc */
    public  static final String PUBLIC_STATIC_FINAL_VAR = "test";

    protected String protectedVar2 = "test"; // violation, 'Missing a Javadoc comment'

    /** Some javadoc */
    private String privateVar = "test";

    /** Some javadoc */
    protected String protectedVar = "test";

    /** Some javadoc */
    java.lang.String state = "test";

    java.lang.String state2 = "test"; // violation, 'Missing a Javadoc comment'
}
