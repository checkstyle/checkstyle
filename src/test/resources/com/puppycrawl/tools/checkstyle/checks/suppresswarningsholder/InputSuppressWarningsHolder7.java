package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

import java.lang.annotation.*;
import java.util.List;

public class InputSuppressWarningsHolder7 {

    @TestSwAnnotation(@SuppressWarnings("unchecked"))
    private List<String> testSwAnnotation;

    @TestSwAnnotationVal(onMethod = @SuppressWarnings("unchecked"))
    private List<String> testSwAnnotationVal;
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface TestSwAnnotation {
    SuppressWarnings value();
}

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
@interface TestSwAnnotationVal{

    SuppressWarnings[] onMethod() default {};

    @Deprecated
    @Retention(RetentionPolicy.SOURCE)
    @Target({})
    @interface AnyAnnotation {}
}
