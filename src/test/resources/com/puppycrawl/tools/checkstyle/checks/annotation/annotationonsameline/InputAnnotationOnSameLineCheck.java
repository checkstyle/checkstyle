/*
AnnotationOnSameLine
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

public class InputAnnotationOnSameLineCheck {

    @Annotation int x;      // ok

    int y;

    @Annotation             // violation
    @SomeClass.Annotation   // violation
    @java.lang.Deprecated   // violation
    public int getX() {
        return (int) x;
    }

    @Annotation2 @Annotation    // violation
    public int field;

    public
    @Annotation int field2; // ok
}

class SomeClass {

    @interface Annotation {}
}

@interface Annotation {}
@interface Annotation2 {}
