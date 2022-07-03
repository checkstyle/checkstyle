/*
MissingJavadocType
scope = (default)public
excludeScope = (default)null
skipAnnotations = com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype.\
    InputMissingJavadocTypeFullyQualifiedAnnotation4.SomeAnnotation
tokens = INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

public class InputMissingJavadocTypeFullyQualifiedAnnotation4 {
    public @interface SomeAnnotation { }

    @SomeAnnotation // violation 'Missing a Javadoc comment.'
    public interface A { }

    // violation below 'Missing a Javadoc comment.'
    @InputMissingJavadocTypeFullyQualifiedAnnotation4.SomeAnnotation
    public interface B { }

    @com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype. // ok
        InputMissingJavadocTypeFullyQualifiedAnnotation4.SomeAnnotation
    public interface C { }
}
