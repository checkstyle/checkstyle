/*
MissingJavadocType
scope = (default)public
excludeScope = (default)null
skipAnnotations = (default)Generated
tokens = INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

public class InputMissingJavadocTypeQualifiedAnnotation1 {
    public @interface SomeAnnotation { }

    @SomeAnnotation // violation 'Missing a Javadoc comment.'
    public interface A { }

    // violation below 'Missing a Javadoc comment.'
    @InputMissingJavadocTypeQualifiedAnnotation1.SomeAnnotation
    public interface B { }

    @com.puppycrawl.tools.checkstyle.checks.javadoc // violation 'Missing a Javadoc comment.'
        .missingjavadoctype.InputMissingJavadocTypeQualifiedAnnotation1.SomeAnnotation
    public interface C { }
}
