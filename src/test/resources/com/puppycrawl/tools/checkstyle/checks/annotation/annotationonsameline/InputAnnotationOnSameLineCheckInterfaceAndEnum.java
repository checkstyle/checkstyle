/*
AnnotationOnSameLine
tokens = CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, \
         VARIABLE_DEF, PARAMETER_DEF, ANNOTATION_DEF, TYPECAST, LITERAL_THROWS, \
         IMPLEMENTS_CLAUSE, TYPE_ARGUMENT, LITERAL_NEW, DOT, ANNOTATION_FIELD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

import java.util.List;

// violation below "Annotation 'Ann' should be on the same line with its target."
@Ann
@Ann2 interface TestInterface {

    // violation below "Annotation 'Ann' should be on the same line with its target."
    @Ann
    @Ann2 Integer getX();
}

// violation 2 lines below "Annotation 'Ann' should be on the same line with its target."
// violation 2 lines below "Annotation 'Ann' should be on the same line with its target."
public @Ann
@Ann2 class InputAnnotationOnSameLineCheckInterfaceAndEnum implements @Ann
        @Ann2 TestInterface {

    // violation 2 lines below "Annotation 'Ann' should be on the same line with its target."
    // violation 2 lines below "Annotation 'Ann' should be on the same line with its target."
    @Ann
    @Ann2 private Integer x = new @Ann
            @Ann2 Integer(0);

    // violation below "Annotation 'Ann' should be on the same line with its target."
    private List<@Ann
            @Ann2 Integer> integerList;

    // violation below "Annotation 'Ann' should be on the same line with its target."
    @Ann
    @Ann2 enum TestEnum {
        A1, A2
    }

    // violation below "Annotation 'Ann' should be on the same line with its target."
    @Ann
    @Ann2 public InputAnnotationOnSameLineCheckInterfaceAndEnum() {}

    // violation 2 lines below "Annotation 'Ann' should be on the same line with its target."
    // violation 2 lines below "Annotation 'Ann' should be on the same line with its target."
    @Ann
    @Ann2 public void setX(@Ann
            // violation below "Annotation 'Ann' should be on the same line with its target."
            @Ann2 int x) throws @Ann
                    @Ann2 Exception {

        // violation below "Annotation 'Ann' should be on the same line with its target."
        this.<@Ann
                @Ann2 Integer> getXAs();
        this.x = x;
    }

    @Override public Integer getX() {
        // violation below "Annotation 'Ann' should be on the same line with its target."
        return (@Ann
                @Ann2 Integer) x;
    }

    public <@Ann T> T getXAs() {
        return (T) x;
    }
}

// violation below "Annotation 'Ann' should be on the same line with its target."
@Ann
@Ann2 @interface TestAnnotation {

    // violation below "Annotation 'Ann' should be on the same line with its target."
    @Ann
    @Ann2 int x();
}
