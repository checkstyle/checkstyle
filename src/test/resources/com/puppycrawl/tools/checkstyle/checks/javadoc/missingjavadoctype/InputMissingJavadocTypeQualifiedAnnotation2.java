/*
MissingJavadocType
scope = (default)public
excludeScope = (default)null
skipAnnotations = SomeAnnotation
tokens = INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

public class InputMissingJavadocTypeQualifiedAnnotation2 {
    public @interface SomeAnnotation { }

    @SomeAnnotation
    public interface A { }

    // violation below 'Missing a Javadoc comment.'
    @InputMissingJavadocTypeQualifiedAnnotation2.SomeAnnotation
    public interface B { }

    @com.puppycrawl.tools.checkstyle.checks.javadoc // violation 'Missing a Javadoc comment.'
        .missingjavadoctype.InputMissingJavadocTypeQualifiedAnnotation2.SomeAnnotation
    public interface C { }
}
