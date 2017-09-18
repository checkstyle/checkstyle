package com.puppycrawl.tools.checkstyle.checks.metrics.classfanoutcomplexity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputClassFanOutComplexityAnnotations {

    public void foo1(@TypeAnnotation char a){}

    public void foo2(final char @TypeAnnotation [] a){}

    @MethodAnnotation
    public void foo3(){}

    @Override
    public String toString() {
        return super.toString();
    }

}

@Target(ElementType.TYPE_USE)
@interface TypeAnnotation {}

@Target(ElementType.METHOD)
@interface MethodAnnotation {}

@MyAnnotation
class MyClass{}

@MyAnnotation
interface MyInterface{}

@interface MyAnnotation{}
