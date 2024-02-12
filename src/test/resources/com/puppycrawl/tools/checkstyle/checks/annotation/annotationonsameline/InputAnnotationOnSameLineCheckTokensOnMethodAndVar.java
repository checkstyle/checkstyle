/*
AnnotationOnSameLine
tokens = CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, \
         VARIABLE_DEF, PARAMETER_DEF, ANNOTATION_DEF, TYPECAST, LITERAL_THROWS, \
         IMPLEMENTS_CLAUSE, TYPE_ARGUMENT, LITERAL_NEW, DOT, ANNOTATION_FIELD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

public class InputAnnotationOnSameLineCheckTokensOnMethodAndVar {

    @Annotation3 int x;

    int y;

    @Annotation3            // violation
    @SomeClass2.Annotation  // violation
    @java.lang.Deprecated   // violation
    public int getX() {
        return (int) x;
    }

    @Annotation4 @Annotation3  // violation
    public int field;

    public
    @Annotation3 int field2;
}

class SomeClass2 {

    @interface Annotation {}
}

@interface Annotation3 {}
@interface Annotation4 {}
