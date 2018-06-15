package com.puppycrawl.tools.checkstyle.checks.annotation.annotationusestyle;
// this file compiles in jdk but NOT with eclipse 
// eclipse error "The value for annotation attribute must be a constant expression"
public class InputAnnotationUseStyleWithTrailingComma
{
    @Test(value={(false) ? "" : "foo",}, more={(true) ? "" : "bar",})
    /**

    */
    enum P {
        L,
        Y;
    }
    
}

@interface Test {
    String[] value();
    String[] more() default {};
}
