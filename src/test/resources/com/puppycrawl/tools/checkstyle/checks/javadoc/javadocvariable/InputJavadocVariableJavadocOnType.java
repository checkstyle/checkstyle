/*
JavadocVariable
accessModifiers = (default)public,protected,package,private
ignoreNamePattern = (default)null
tokens = ENUM_CONSTANT_DEF, VARIABLE_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

public class InputJavadocVariableJavadocOnType {

    // Javadoc placed between modifier and type
    public /** some javadoc */ int fieldWithJavadocOnType;

    public int fieldWithoutJavadoc; // violation, 'Missing a Javadoc comment.'
    public /* not javadoc */ int fieldWithRegularComment; // violation, 'Missing a Javadoc comment.'
}

