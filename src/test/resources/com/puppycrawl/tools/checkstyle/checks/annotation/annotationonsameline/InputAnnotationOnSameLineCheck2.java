package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Target;
import java.util.List;
import java.util.ArrayList;

public class InputAnnotationOnSameLineCheck2 {

    @Ann
    private List<String> names = new ArrayList<>();

    @Ann private List<String> names2 = new ArrayList<>();

    @SuppressWarnings("deprecation")
    @Ann Integer x;

    @SuppressWarnings("deprecation")
    @Ann
    Integer x2;

    @SuppressWarnings("deprecation") @Ann @Ann2 @Ann3 @Ann4 Integer x3;

}

@Target({CONSTRUCTOR, FIELD, METHOD, PARAMETER, TYPE, TYPE_PARAMETER, TYPE_USE}) @interface Ann {
}

@Target({CONSTRUCTOR, FIELD, METHOD, PARAMETER, TYPE, TYPE_PARAMETER, TYPE_USE}) @interface Ann2 {
}

@interface Ann3 {
}

@interface Ann4 {
}
