/*
MissingJavadocType
scope = (default)public
excludeScope = (default)null
skipAnnotations = Ann1, AnnClass.Ann3
tokens = INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

@interface Ann1 { }
@interface Ann2 { }
@interface Ann3 { }

class AnnClass {
    public @interface Ann1 { }
    public @interface Ann2 { }
    public @interface Ann3 { }
}

public class InputMissingJavadocTypeMultipleQualifiedAnnotation {
    @Ann1
    @Ann2
    @Ann3
    public interface A { }

    @Ann2 // violation 'Missing a Javadoc comment.'
    @Ann3
    public interface B { }

    @Ann2
    @Ann3
    @Ann1
    public interface C { }

    @AnnClass.Ann1 // violation 'Missing a Javadoc comment.'
    @Ann2
    @Ann3
    public interface D { }

    @AnnClass.Ann2
    @Ann2
    @AnnClass.Ann3
    public interface E { }
}
