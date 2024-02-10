/*
MissingJavadocType
scope = (default)public
excludeScope = (default)null
skipAnnotations = InputMissingJavadocTypeQualifiedAnnotation3.SomeAnnotation
tokens = INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

public class InputMissingJavadocTypeQualifiedAnnotation3 {
    public @interface SomeAnnotation { }

    @SomeAnnotation // violation 'Missing a Javadoc comment.'
    public interface A { }

    @InputMissingJavadocTypeQualifiedAnnotation3.SomeAnnotation
    public interface B { }

    @com.puppycrawl.tools.checkstyle.checks.javadoc // violation 'Missing a Javadoc comment.'
        .missingjavadoctype.InputMissingJavadocTypeQualifiedAnnotation3.SomeAnnotation
    public interface C { }
}
