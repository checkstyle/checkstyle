/*
MissingJavadocType
scope = (default)public
excludeScope = (default)null
skipAnnotations = SomeAnnotation, InputMissingJavadocTypeQualifiedAnnotation5.SomeAnnotation, \
    com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype.\
    InputMissingJavadocTypeQualifiedAnnotation5.SomeAnnotation
tokens = INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

public class InputMissingJavadocTypeQualifiedAnnotation5 {
    public @interface SomeAnnotation { }

    @SomeAnnotation // ok
    public interface A { }

    @InputMissingJavadocTypeQualifiedAnnotation5.SomeAnnotation // ok
    public interface B { }

     @com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype // ok
         .InputMissingJavadocTypeQualifiedAnnotation5.SomeAnnotation
    public interface C { }
}
