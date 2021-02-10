package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

import java.lang.annotation.*;
import java.util.List;

public class InputSuppressWarningsHolder7 {

    @TestSwAnnotation(@SuppressWarnings("unchecked"))
    private List<String> testSwAnnotation;

    @TestSwAnnotationVal(value = @SuppressWarnings("unchecked"))
    private List<String> testSwAnnotationVal;

    @TestSwAnnotationVal(value = {@SuppressWarnings("unchecked")})
    private List<String> list1;

    @TestSwAnnotationVal({@SuppressWarnings("unchecked"), @SuppressWarnings("unchecked")})
    private List<String> list2;
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface TestSwAnnotation {
    SuppressWarnings value() default @SuppressWarnings({"",});
}

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
@interface TestSwAnnotationVal{

    SuppressWarnings[] value() default {@SuppressWarnings({"",})};

    @Deprecated
    @Retention(RetentionPolicy.SOURCE)
    @Target({})
    @interface AnyAnnotation {}
}
