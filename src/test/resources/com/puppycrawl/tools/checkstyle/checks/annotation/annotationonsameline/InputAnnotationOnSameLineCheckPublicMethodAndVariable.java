/*
AnnotationOnSameLine
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

public class InputAnnotationOnSameLineCheckPublicMethodAndVariable {

    @Annotation int x;

    int y;

    // violation 3 lines below "Annotation 'Annotation' should be on the same line with its target."
    // violation 3 lines below "Annotation 'Annotation' should be on the same line with its target."
    // violation 3 lines below "Annotation 'Deprecated' should be on the same line with its target."
    @Annotation
    @SomeClass.Annotation
    @java.lang.Deprecated
    public int getX() {
        return (int) x;
    }

    // 2 violations 3 lines below:
    //    'Annotation 'Annotation2' should be on the same line with its target.'
    //    'Annotation 'Annotation' should be on the same line with its target.'
    @Annotation2 @Annotation
    public int field;

    public
    @Annotation int field2;
}

class SomeClass {

    @interface Annotation {}
}

@interface Annotation {}
@interface Annotation2 {}
