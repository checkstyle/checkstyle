/*
JavadocVariable
accessModifiers = (default)public,protected,package,private
ignoreNamePattern = (default)null
tokens = ENUM_CONSTANT_DEF, VARIABLE_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

public class InputJavadocVariableOnAnnotatedField {

    /** Javadoc above annotation. */
    @SuppressWarnings("unused")
    private int annotatedFieldWithJavadoc;

    @SuppressWarnings("unused") // violation, 'Missing a Javadoc comment.'
    private int annotatedFieldWithoutJavadoc;
}


