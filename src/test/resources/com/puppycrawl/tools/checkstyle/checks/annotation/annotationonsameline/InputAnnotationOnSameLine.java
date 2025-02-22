/*
AnnotationOnSameLine
tokens = CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, \
         VARIABLE_DEF, PARAMETER_DEF, ANNOTATION_DEF, TYPECAST, LITERAL_THROWS, \
         IMPLEMENTS_CLAUSE, TYPE_ARGUMENT, LITERAL_NEW, DOT, ANNOTATION_FIELD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

import static java.lang.annotation.ElementType.TYPE_USE;

import java.lang.annotation.Target;
import java.util.List;

public class InputAnnotationOnSameLine {
    // violation below, "Annotation 'Ann' should be on the same line with its target."
    @Ann public
    @Ann2 class E {}

    // violation below, "Annotation 'Ann' should be on the same line with its target."
    @Ann private
    @Ann2 class A {}
    public void wildcardCase() {
        List<@Ann ?> list;
    }

    public String typeCastCase() {
        Object s = new String();
        return (@Ann String) s;
    }

    public void wildcardCase1() {
        // violation below, 'Annotation 'Ann' should be on the same line with its target.'
        List<@Ann
                ?> list;
    }

    // 2 violations 3 lines below:
    //    "Annotation 'Deprecated' should be on the same line with its target."
    //    "Annotation 'SuppressWarnings' should be on the same line with its target."
    @Deprecated @SuppressWarnings("unchecked")
    public void proceed(int parameter1,  int parameter2,  int parameter3,
                        int parameter4,  int parameter5) { }

    @Deprecated @SuppressWarnings("unchecked") public void proceedGood(int parameter1,
                                                      int parameter2, int parameter3,
                                                      int parameter4,  int parameter5) { }

    int[][][] i = new @Ann3(integer = 1) int[0][][];

    @Target({TYPE_USE}) @interface Ann {}
    @Target({TYPE_USE}) @interface Ann2 {}
    @Target({TYPE_USE}) @interface Ann3 { int integer();}

}
