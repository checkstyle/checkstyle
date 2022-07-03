/*
MissingJavadocType
scope = (default)public
excludeScope = (default)null
skipAnnotations = SomeAnnotation
tokens = INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

public class InputMissingJavadocTypeFullyQualifiedAnnotation2 {
    public @interface SomeAnnotation { }

    @SomeAnnotation // ok
    public interface A { }

    // violation below 'Missing a Javadoc comment.'
    @InputMissingJavadocTypeFullyQualifiedAnnotation2.SomeAnnotation
    public interface B { }

    @com.puppycrawl.tools.checkstyle.checks.javadoc. // violation 'Missing a Javadoc comment.'
        missingjavadoctype.InputMissingJavadocTypeFullyQualifiedAnnotation2.SomeAnnotation
    public interface C { }
}
