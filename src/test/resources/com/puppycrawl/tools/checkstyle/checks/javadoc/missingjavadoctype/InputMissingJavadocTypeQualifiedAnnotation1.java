/*
MissingJavadocType
excludeScope = (default)null
scope = (default)public
skipAnnotations = (default)Generated
violateExecutionOnNonTightHtml = (default)false
tokens = INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

public class InputMissingJavadocTypeQualifiedAnnotation1 {
    public @interface SomeAnnotation { }

    // violation below 'Missing a Javadoc comment.'
    @SomeAnnotation
    public interface A { }

    // violation below 'Missing a Javadoc comment.'
    @InputMissingJavadocTypeQualifiedAnnotation1.SomeAnnotation
    public interface B { }

    // violation below 'Missing a Javadoc comment.'
    @com.puppycrawl.tools.checkstyle.checks.javadoc
        .missingjavadoctype.InputMissingJavadocTypeQualifiedAnnotation1.SomeAnnotation
    public interface C { }
}
