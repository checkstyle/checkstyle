/*
AnnotationOnSameLine
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

public class InputAnnotationOnSameLineCheckPublicMethodAndVariable {

    @Annotation int x;

    int y;

    @Annotation
    // violation above, "Annotation 'Annotation' should be on the same line with its target."
    @SomeClass.Annotation
    // violation above, "Annotation 'Annotation' should be on the same line with its target."
    @java.lang.Deprecated
    // violation above, "Annotation 'Deprecated' should be on the same line with its target."
    public int getX() {
        return (int) x;
    }

    @Annotation2 @Annotation
    // violation above, "Annotation 'Annotation' should be on the same line with its target."
    public int field;

    public
    @Annotation int field2;
}

class SomeClass {

    @interface Annotation {}
}

@interface Annotation {}
@interface Annotation2 {}
