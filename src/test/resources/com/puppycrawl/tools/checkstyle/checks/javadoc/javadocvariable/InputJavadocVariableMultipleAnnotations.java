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

    /** Javadoc on field with annotation. */
    @Deprecated
    private int fieldWithJavadocOnFirstAnnotation;

    /** Javadoc with public modifier and annotation. */
    public @Deprecated
    int fieldWithPublicAndAnnotation;

    @SuppressWarnings("unused") // violation, 'Missing a Javadoc comment.
    public /* not a javadoc comment */ int fieldWithAnnotationAndBlockComment;

    @Deprecated
    @SuppressWarnings("unused")
    /** Javadoc after both annotations. */
    private int fieldWithJavadocAfterAnnotations;
}
