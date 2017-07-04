package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

@MyAnnotation11 @MyAnnotation12 @MyAnnotation13
public class InputAnnotationLocationCustomAnnotationsDeclared {

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
