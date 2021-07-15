package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.TYPE_PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class InputAntlr4AstRegressionAnnotationOnSameLine {
    @Ann        // ok
    private List<String> names = new ArrayList<>();

    @Ann private List<String> names2 = new ArrayList<>();       // violation

    @SuppressWarnings("deprecation")        // violation
    @Ann Integer x;

    @SuppressWarnings("deprecation")        // violation
    @Ann                                    // violation
    Integer x2;

    @SuppressWarnings("deprecation") @Ann @Ann2 @Ann3 @Ann4 Integer x3;     // ok

}

@Target({CONSTRUCTOR, FIELD, METHOD, PARAMETER, TYPE, TYPE_PARAMETER, TYPE_USE}) @interface Ann {}

@Target({CONSTRUCTOR, FIELD, METHOD, PARAMETER, TYPE, TYPE_PARAMETER, TYPE_USE}) @interface Ann2 {}

@interface Ann3 {}

@interface Ann4 {}
