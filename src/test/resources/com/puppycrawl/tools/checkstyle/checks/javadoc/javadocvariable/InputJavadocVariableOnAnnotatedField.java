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

    /** Javadoc with public modifier and annotation. */
    public @Deprecated
    int fieldWithPublicAndAnnotation;

    public static final int fieldWithModifiersOnly = 1; // violation, 'Missing a Javadoc comment.'

    /** Javadoc on field with annotation. */
    @SuppressWarnings("unused")
    public static final int fieldWithAnnotationAndJavadoc = 2;

    private static int fieldWithModifiersNoAnnotation = 3; // violation, 'Missing'
}
