/*
MissingJavadocType
excludeScope = (default)null
scope = (default)public
skipAnnotations = com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype.\
    InputMissingJavadocTypeQualifiedAnnotation4.SomeAnnotation
violateExecutionOnNonTightHtml = (default)false
tokens = INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

public class InputMissingJavadocTypeQualifiedAnnotation4 {
    public @interface SomeAnnotation { }

    // violation below 'Missing a Javadoc comment.'
    @SomeAnnotation
    public interface A { }

    // violation below 'Missing a Javadoc comment.'
    @InputMissingJavadocTypeQualifiedAnnotation4.SomeAnnotation
    public interface B { }

     @com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype
         .InputMissingJavadocTypeQualifiedAnnotation4.SomeAnnotation
    public interface C { }
}
