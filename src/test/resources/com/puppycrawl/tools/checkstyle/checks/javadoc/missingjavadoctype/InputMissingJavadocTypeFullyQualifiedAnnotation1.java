/*
MissingJavadocType
scope = (default)public
excludeScope = (default)null
skipAnnotations = (default)Generated
tokens = INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

public class InputMissingJavadocTypeFullyQualifiedAnnotation1 {
    public @interface SomeAnnotation { }

    @SomeAnnotation // violation
    public interface A { }

    @InputMissingJavadocTypeFullyQualifiedAnnotation1.SomeAnnotation // violation
    public interface B { }

    @com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype. // violation
        InputMissingJavadocTypeFullyQualifiedAnnotation1.SomeAnnotation
    public interface C { }
}
