package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

class InputIndentationAnnotationIncorrect { //indent:0 exp:0

    public @interface MyAnnotation1 { //indent:4 exp:4
        String value(); //indent:8 exp:8
    } //indent:4 exp:4

    @MyAnnotation2 //indent:4 exp:4
    @MyAnnotation1 //indent:4 exp:4
    (value = "") //indent:4 exp:8 warn
    class innerClass { //indent:4 exp:4
        @MyAnnotation2 @MyAnnotation1 //indent:8 exp:8
        (value = "") //indent:8 exp:12 warn
        public int a; //indent:8 exp:8
    } //indent:4 exp:4

    @MyAnnotation2 @MyAnnotation1 //indent:4 exp:4
    (value = "") //indent:4 exp:8 warn
    class InputIndentationAnnotationInnerClass { //indent:4 exp:4

    } //indent:4 exp:4
} //indent:0 exp:0

