/*
AnnotationOnSameLine
tokens = CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, \
         VARIABLE_DEF, PARAMETER_DEF, ANNOTATION_DEF, TYPECAST, LITERAL_THROWS, \
         IMPLEMENTS_CLAUSE, TYPE_ARGUMENT, LITERAL_NEW, DOT, ANNOTATION_FIELD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

import java.util.List;

@Ann        // violation, "Annotation 'Ann' should be on the same line with its target."
@Ann2 interface TestInterface {

    @Ann    // violation, "Annotation 'Ann' should be on the same line with its target."
    @Ann2 Integer getX();
}

public @Ann     // violation, "Annotation 'Ann' should be on the same line with its target."
                // violation below, "Annotation 'Ann' should be on the same line with its target."
@Ann2 class InputAnnotationOnSameLineCheckInterfaceAndEnum implements @Ann
        @Ann2 TestInterface {

    @Ann        // violation, "Annotation 'Ann' should be on the same line with its target."
    @Ann2 private Integer x = new @Ann // violation, should be on the same line with its target."
            @Ann2 Integer(0);

            // violation below, "Annotation 'Ann' should be on the same line with its target."
    private List<@Ann
            @Ann2 Integer> integerList;

    @Ann        // violation, "Annotation 'Ann' should be on the same line with its target."
    @Ann2 enum TestEnum {
        A1, A2
    }

    @Ann        // violation, "Annotation 'Ann' should be on the same line with its target."

    @Ann2 public InputAnnotationOnSameLineCheckInterfaceAndEnum() {}

    @Ann        // violation, "Annotation 'Ann' should be on the same line with its target."
                // violation below, "Annotation 'Ann' should be on the same line with its target."
    @Ann2 public void setX(@Ann

            @Ann2 int x) throws @Ann // violation, should be on the same line with its target."
                    @Ann2 Exception {

        this.<@Ann // violation, "Annotation 'Ann' should be on the same line with its target."
                @Ann2 Integer> getXAs();
        this.x = x;
    }

    @Override public Integer getX() {
        return (@Ann // violation, "Annotation 'Ann' should be on the same line with its target."
                @Ann2 Integer) x;
    }

    public <@Ann T> T getXAs() {
        return (T) x;
    }
}

@Ann        // violation, "Annotation 'Ann' should be on the same line with its target."
@Ann2 @interface TestAnnotation {

    @Ann    // violation, "Annotation 'Ann' should be on the same line with its target."
    @Ann2 int x();
}
