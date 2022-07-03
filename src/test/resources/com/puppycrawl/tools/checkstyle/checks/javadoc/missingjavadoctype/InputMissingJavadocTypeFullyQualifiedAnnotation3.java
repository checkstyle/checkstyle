/*
MissingJavadocType
scope = (default)public
excludeScope = (default)null
skipAnnotations = InputMissingJavadocTypeFullyQualifiedAnnotation3.SomeAnnotation
tokens = INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

public class InputMissingJavadocTypeFullyQualifiedAnnotation3 {
    public @interface SomeAnnotation { }

    @SomeAnnotation // violation
    public interface A { }

    @InputMissingJavadocTypeFullyQualifiedAnnotation3.SomeAnnotation // ok
    public interface B { }

    @com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype. // violation
        InputMissingJavadocTypeFullyQualifiedAnnotation3.SomeAnnotation
    public interface C { }
}
