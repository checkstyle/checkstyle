package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;    //indent:0 exp:0

@MyAnn_22                                                                  //indent:0 exp:0
        @MyAnnotation_12                                                   //indent:8 exp:0 warn
class InputIndentationAnnotationOnlyModifiers {                            //indent:0 exp:0

    @MyAnnotation_12                                                       //indent:4 exp:4
            @MyAnn_22                                                      //indent:12 exp:4 warn
    class InnerClass {                                                     //indent:4 exp:4
    }                                                                      //indent:4 exp:4
}                                                                          //indent:0 exp:0

@interface MyAnnotation_12 {                                               //indent:0 exp:0
    String value() default "";                                             //indent:4 exp:4
}                                                                          //indent:0 exp:0

@interface MyAnn_22 {                                                      //indent:0 exp:0
}                                                                          //indent:0 exp:0
