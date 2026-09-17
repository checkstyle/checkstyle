/*
MissingJavadocType
excludeScope = (default)null
scope = (default)public
skipAnnotations = SomeAnnotation
violateExecutionOnNonTightHtml = (default)false
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

    // violation below 'Missing a Javadoc comment.'
    @com.puppycrawl.tools.checkstyle.checks.javadoc
        .missingjavadoctype.InputMissingJavadocTypeQualifiedAnnotation2.SomeAnnotation
    public interface C { }
}
