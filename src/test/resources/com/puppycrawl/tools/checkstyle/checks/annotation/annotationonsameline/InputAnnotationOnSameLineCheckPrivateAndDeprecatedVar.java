/*
AnnotationOnSameLine
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Target;
import java.util.List;
import java.util.ArrayList;

public class InputAnnotationOnSameLineCheckPrivateAndDeprecatedVar {

    @Ann        // violation, "Annotation 'Ann' should be on the same line with its target."
    private List<String> names = new ArrayList<>();

    @Ann private List<String> names2 = new ArrayList<>();

    // violation below, "Annotation 'SuppressWarnings' should be on the same line with its target."
    @SuppressWarnings("deprecation")
    @Ann Integer x;

    // violation below, "Annotation 'SuppressWarnings' should be on the same line with its target."
    @SuppressWarnings("deprecation")
    @Ann
    // violation above, "Annotation 'Ann' should be on the same line with its target."
    Integer x2;

    @SuppressWarnings("deprecation") @Ann @Ann2 @Ann3 @Ann4 Integer x3;

}

@Target({CONSTRUCTOR, FIELD, METHOD, PARAMETER, TYPE, TYPE_PARAMETER, TYPE_USE}) @interface Ann {}

@Target({CONSTRUCTOR, FIELD, METHOD, PARAMETER, TYPE, TYPE_PARAMETER, TYPE_USE}) @interface Ann2 {}

@interface Ann3 {}

@interface Ann4 {}
