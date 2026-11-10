package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;  //indent:0 exp:0

import java.lang.annotation.Retention;                                   //indent:0 exp:0
import java.lang.annotation.RetentionPolicy;                             //indent:0 exp:0

class InputIndentationInvalidAnnotationIndent {                          //indent:0 exp:0
    @InvalidIndentAnno1                                                  //indent:4 exp:4
     @InvalidIndentAnno2                                                 //indent:5 exp:4 warn
      @InvalidIndentAnno3                                                //indent:6 exp:4 warn
     @InvalidIndentAnno4                                                 //indent:5 exp:4 warn
    @InvalidIndentAnno5                                                  //indent:4 exp:4
             public void testMethod() {                                  //indent:13 exp:4 warn
    }                                                                    //indent:4 exp:4

     @InvalidIndentAnno1                                                 //indent:5 exp:4 warn
    @InvalidIndentAnno2                                                  //indent:4 exp:4
    public void testMethod2() {                                          //indent:4 exp:4
    }                                                                    //indent:4 exp:4

    @InvalidIndentAnno1                                                  //indent:4 exp:4
     @InvalidIndentAnno2                                                 //indent:5 exp:4 warn
    private int field1;                                                  //indent:4 exp:4

    @InvalidIndentAnno1                                                  //indent:4 exp:4
    @InvalidIndentAnno2                                                  //indent:4 exp:4
             private int field2;                                         //indent:13 exp:4 warn

    @InvalidIndentAnno1                                                  //indent:4 exp:4
     @InvalidIndentAnno2                                                 //indent:5 exp:4 warn
    void testMethod3() {                                                 //indent:4 exp:4
    }                                                                    //indent:4 exp:4

    @InvalidIndentAnno1                                                  //indent:4 exp:4
     @InvalidIndentAnno2                                                 //indent:5 exp:4 warn
    int field3;                                                          //indent:4 exp:4

    @InvalidIndentAnno1                                                  //indent:4 exp:4
     @InvalidIndentAnno2                                                 //indent:5 exp:4 warn
    public InputIndentationInvalidAnnotationIndent() {                   //indent:4 exp:4
    }                                                                    //indent:4 exp:4

    @InvalidIndentAnno1                                                  //indent:4 exp:4
     @InvalidIndentAnno2                                                 //indent:5 exp:4 warn
    public <T> T testMethod4(T input) {                                  //indent:4 exp:4
        return input;                                                    //indent:8 exp:8
    }                                                                    //indent:4 exp:4

    @InvalidIndentAnno3({                                                //indent:4 exp:4
        "a",                                                             //indent:8 exp:8
        "b"                                                              //indent:8 exp:8
    })                                                                   //indent:4 exp:4
     public void testMethod5() {                                         //indent:5 exp:4 warn
    }                                                                    //indent:4 exp:4

    class InnerClass {                                                   //indent:4 exp:4
        @InvalidIndentAnno1                                              //indent:8 exp:8
         @InvalidIndentAnno2                                             //indent:9 exp:8 warn
        public void innerMethod() {                                      //indent:8 exp:8
        }                                                                //indent:8 exp:8
    }                                                                    //indent:4 exp:4

    public void testMethod6() {                                          //indent:4 exp:4
        @InvalidIndentAnno1                                              //indent:8 exp:8
         @InvalidIndentAnno2                                             //indent:9 exp:8 warn
        int local = 5;                                                   //indent:8 exp:8
    }                                                                    //indent:4 exp:4
}                                                                        //indent:0 exp:0

@InvalidIndentAnno1                                                      //indent:0 exp:0
@InvalidIndentAnno2                                                      //indent:0 exp:0
             class InvalidAnnotationIndentClass {                        //indent:13 exp:0 warn
}                                                                        //indent:0 exp:0

@InvalidIndentAnno1                                                      //indent:0 exp:0
@InvalidIndentAnno2                                                      //indent:0 exp:0
             interface InvalidAnnotationIndentInterface {                //indent:13 exp:0 warn
}                                                                        //indent:0 exp:0

@InvalidIndentAnno1                                                      //indent:0 exp:0
@InvalidIndentAnno2                                                      //indent:0 exp:0
             record InvalidAnnotationIndentRecord(int value) {           //indent:13 exp:0 warn
}                                                                        //indent:0 exp:0

@InvalidIndentAnno1                                                      //indent:0 exp:0
@InvalidIndentAnno2                                                      //indent:0 exp:0
             enum InvalidAnnotationIndentEnum {                          //indent:13 exp:0 warn
    VALUE                                                                //indent:4 exp:4
}                                                                        //indent:0 exp:0

@interface InvalidAnnotationIndentAnno {                                 //indent:0 exp:0
    @InvalidIndentAnno1                                                  //indent:4 exp:4
     @InvalidIndentAnno2                                                 //indent:5 exp:4 warn
    String value();                                                      //indent:4 exp:4
}                                                                        //indent:0 exp:0

record InvalidAnnotationIndentRecord2(int value) {                       //indent:0 exp:0
    @InvalidIndentAnno1                                                  //indent:4 exp:4
     @InvalidIndentAnno2                                                 //indent:5 exp:4 warn
    public InvalidAnnotationIndentRecord2 {                              //indent:4 exp:4
    }                                                                    //indent:4 exp:4
}                                                                        //indent:0 exp:0

@Retention(RetentionPolicy.RUNTIME)                                      //indent:0 exp:0
@interface InvalidIndentAnno1 {                                          //indent:0 exp:0
}                                                                        //indent:0 exp:0

@Retention(RetentionPolicy.RUNTIME)                                      //indent:0 exp:0
@interface InvalidIndentAnno2 {                                          //indent:0 exp:0
}                                                                        //indent:0 exp:0

@Retention(RetentionPolicy.RUNTIME)                                      //indent:0 exp:0
@interface InvalidIndentAnno3 {                                          //indent:0 exp:0
    String[] value() default {};                                         //indent:4 exp:4
}                                                                        //indent:0 exp:0

@Retention(RetentionPolicy.RUNTIME)                                      //indent:0 exp:0
@interface InvalidIndentAnno4 {                                          //indent:0 exp:0
}                                                                        //indent:0 exp:0

@Retention(RetentionPolicy.RUNTIME)                                      //indent:0 exp:0
@interface InvalidIndentAnno5 {                                          //indent:0 exp:0
}                                                                        //indent:0 exp:0
