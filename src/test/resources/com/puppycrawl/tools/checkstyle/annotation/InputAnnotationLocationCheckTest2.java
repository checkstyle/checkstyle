package com.puppycrawl.tools.checkstyle.annotation;

@MyAnnotation11 @MyAnnotation12 @MyAnnotation13
public class InputAnnotationLocationCheckTest2 {

    @MyAnnotation13
    void method() {
        
    }
    
    @MyAnnotation13
    @MyAnnotation12
    void method2() {
        
    }
	
}

@interface MyAnnotation11 {
}

@interface MyAnnotation12 {
}

@interface MyAnnotation13 {
}