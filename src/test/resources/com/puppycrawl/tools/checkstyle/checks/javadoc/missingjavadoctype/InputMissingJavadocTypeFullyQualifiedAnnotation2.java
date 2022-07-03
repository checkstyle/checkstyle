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

    @InputMissingJavadocTypeFullyQualifiedAnnotation2.SomeAnnotation // violation
    public interface B { }

    @com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype. // violation
        InputMissingJavadocTypeFullyQualifiedAnnotation2.SomeAnnotation
    public interface C { }
}
