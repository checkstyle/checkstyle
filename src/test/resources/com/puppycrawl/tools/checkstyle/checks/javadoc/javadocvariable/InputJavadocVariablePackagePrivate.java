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

    java.util.List<String> state3 = null; // violation, 'Missing a Javadoc comment'

    /** Some javadoc */
    java.util.List<String> state4;

    java.util.List<String> state5; // violation, 'Missing a Javadoc comment'

    public /** some javadoc */ java.lang.String fieldWithJavadocOnType2;

    public java.lang.String fieldWithoutJavadoc2; // violation, 'Missing a Javadoc comment.'

    /** Some javadoc */
    java.util.Map.Entry<String, String> deepDotField1;

    java.util.Map.Entry<String, String> deepDotField2; // violation, 'Missing a Javadoc comment'

    /** Some javadoc - To kill pitest mutations */
    java.util.Map./** javadoc on middle dot */ Entry<String, String> deepDotJavadocOnMiddle;

    public /** javadoc at outermost */ java.util.Map.Entry<String, String> deepDotJavadocOnType;
}
