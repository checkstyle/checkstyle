/*
MissingJavadocType
excludeScope = (default)null
scope = (default)public
skipAnnotations = InputMissingJavadocTypeQualifiedAnnotation3.SomeAnnotation
violateExecutionOnNonTightHtml = (default)false
tokens = INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

public class InputMissingJavadocTypeQualifiedAnnotation3 {
    public @interface SomeAnnotation { }

    // violation below 'Missing a Javadoc comment.'
    @SomeAnnotation
    public interface A { }

    @InputMissingJavadocTypeQualifiedAnnotation3.SomeAnnotation
    public interface B { }

    // violation below 'Missing a Javadoc comment.'
    @com.puppycrawl.tools.checkstyle.checks.javadoc
        .missingjavadoctype.InputMissingJavadocTypeQualifiedAnnotation3.SomeAnnotation
    public interface C { }
}
