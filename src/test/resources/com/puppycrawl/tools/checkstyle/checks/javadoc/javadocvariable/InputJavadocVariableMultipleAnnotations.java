/*
JavadocVariable
accessModifiers = (default)public,protected,package,private
ignoreNamePattern = (default)null
tokens = ENUM_CONSTANT_DEF, VARIABLE_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

public class InputJavadocVariableMultipleAnnotations {

    @Deprecated
    /** Javadoc between annotations. */
    @SuppressWarnings("unused")
    private int fieldWithJavadocBetweenAnnotations;

    @Deprecated // violation, 'Missing a Javadoc comment.'
    @SuppressWarnings("unused")
    private int fieldWithMultipleAnnotationsNoJavadoc;

    /** Javadoc on annotation. */
    @Deprecated
    private int fieldWithJavadocOnFirstAnnotation;

    /** Javadoc with public modifier and annotation. */
    public @Deprecated
    int fieldWithPublicAndAnnotation;
}

