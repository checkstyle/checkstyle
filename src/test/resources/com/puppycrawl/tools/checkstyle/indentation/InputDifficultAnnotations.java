package com.puppycrawl.tools.checkstyle.indentation;

import com.puppycrawl.tools.checkstyle.api.JavadocTagInfo.Type;


@DifficultAnnotation({
    @MyType(value = Boolean.class,
        name = "boolean"),
    @MyType(value = String.class, name = "string") })
@SimpleAnnotation
public class InputDifficultAnnotations {

    @DifficultAnnotation({
        @MyType(value = Boolean.class, name = "boolean"),
        @MyType(value = String.class, name = "string") })
    @SimpleAnnotation
    String foo = "foo";

    @DifficultAnnotation({
        @MyType(value = Boolean.class, name = "boolean"),
        @MyType(value = String.class, name = "string") })
    @SimpleAnnotation
    void foo() {
        
    }
}

@DifficultAnnotation({
@MyType(value = Boolean.class, name = "boolean"), //warn
@MyType(value = String.class, name = "string") }) //warn
class IncorrectClass {

    @DifficultAnnotation({
        @MyType(value = Boolean.class, name = "boolean"),
        @MyType(value = String.class, name = "string") })
    String foo = "foo";

    @DifficultAnnotation({
      @MyType(value = Boolean.class, name = "boolean"), //warn
        @MyType(value = String.class, name = "string") })
    void foo() {
            
    }    
}

@interface DifficultAnnotation {

    MyType[] value();

}

@interface MyType {

    Class<?> value();

    String name();
}

@interface SimpleAnnotation {}