package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;  //indent:0 exp:0

import java.lang.annotation.Retention;                                   //indent:0 exp:0
import java.lang.annotation.RetentionPolicy;                             //indent:0 exp:0

class InputIndentationCorrectAnnotationIndent {                          //indent:0 exp:0
    @ValidIndentAnno1                                                    //indent:4 exp:4
    @ValidIndentAnno2                                                    //indent:4 exp:4
    @ValidIndentAnno3                                                    //indent:4 exp:4
    @ValidIndentAnno4                                                    //indent:4 exp:4
    @ValidIndentAnno5                                                    //indent:4 exp:4
    public void testMethod() {                                           //indent:4 exp:4
    }                                                                    //indent:4 exp:4

    @ValidIndentAnno1 @ValidIndentAnno2 public void testMethod2() {      //indent:4 exp:4
    }                                                                    //indent:4 exp:4

    @ValidIndentAnno1                                                    //indent:4 exp:4
    private int field1;                                                  //indent:4 exp:4

    @ValidIndentAnno1                                                    //indent:4 exp:4
    @ValidIndentAnno2                                                    //indent:4 exp:4
    int field2;                                                          //indent:4 exp:4

    @ValidIndentAnno1                                                    //indent:4 exp:4
    @ValidIndentAnno2                                                    //indent:4 exp:4
    void testMethod3() {                                                 //indent:4 exp:4
    }                                                                    //indent:4 exp:4

    @ValidIndentAnno1                                                    //indent:4 exp:4
    @ValidIndentAnno2                                                    //indent:4 exp:4
    public InputIndentationCorrectAnnotationIndent() {                   //indent:4 exp:4
    }                                                                    //indent:4 exp:4

    @ValidIndentAnno1                                                    //indent:4 exp:4
    @ValidIndentAnno2                                                    //indent:4 exp:4
    public <T> T testMethod4(T input) {                                  //indent:4 exp:4
        return input;                                                    //indent:8 exp:8
    }                                                                    //indent:4 exp:4

    @ValidIndentAnno3({                                                  //indent:4 exp:4
        "a",                                                             //indent:8 exp:8
        "b"                                                              //indent:8 exp:8
    })                                                                   //indent:4 exp:4
    public void testMethod5() {                                          //indent:4 exp:4
    }                                                                    //indent:4 exp:4

    @ValidIndentAnno1                                                    //indent:4 exp:4
    public void testMethod6(                                             //indent:4 exp:4
        int param1,                                                      //indent:8 exp:8
        int param2) {                                                    //indent:8 exp:8
    }                                                                    //indent:4 exp:4

    @ValidIndentAnno1                                                    //indent:4 exp:4
    public                                                               //indent:4 exp:4
        static void testMethod7() {                                      //indent:8 exp:8
    }                                                                    //indent:4 exp:4

    @ValidIndentAnno1                                                    //indent:4 exp:4
    public static                                                        //indent:4 exp:4
        void testMethod8() {                                             //indent:8 exp:8
    }                                                                    //indent:4 exp:4

    public                                                               //indent:4 exp:4
        @ValidIndentAnno1                                                //indent:8 exp:8
        void testMethod9() {                                             //indent:8 exp:8
    }                                                                    //indent:4 exp:4

    @ValidIndentAnno1                                                    //indent:4 exp:4
    @ValidIndentAnno2 public void testMethod12() {                       //indent:4 exp:4
    }                                                                    //indent:4 exp:4

    public void testMethod11() {                                         //indent:4 exp:4
        Object obj = java.util.concurrent.Executors.callable(            //indent:8 exp:8
            new Runnable() {                                             //indent:12 exp:12
                @ValidIndentAnno1                                        //indent:16 exp:16
                @Override                                                //indent:16 exp:16
                public void run() {                                      //indent:16 exp:16
                }                                                        //indent:16 exp:16
            });                                                          //indent:12 exp:12
    }                                                                    //indent:4 exp:4

    class InnerClass {                                                   //indent:4 exp:4
        @ValidIndentAnno1                                                //indent:8 exp:8
        @ValidIndentAnno2                                                //indent:8 exp:8
        public void innerMethod() {                                      //indent:8 exp:8
        }                                                                //indent:8 exp:8
    }                                                                    //indent:4 exp:4

    public void testMethod10() {                                         //indent:4 exp:4
        @ValidIndentAnno1                                                //indent:8 exp:8
        @ValidIndentAnno2                                                //indent:8 exp:8
        int local = 5;                                                   //indent:8 exp:8
    }                                                                    //indent:4 exp:4
}                                                                        //indent:0 exp:0

@ValidIndentAnno1                                                        //indent:0 exp:0
@ValidIndentAnno2                                                        //indent:0 exp:0
class CorrectAnnotationIndentClass {                                     //indent:0 exp:0
}                                                                        //indent:0 exp:0

@ValidIndentAnno1                                                        //indent:0 exp:0
@ValidIndentAnno2                                                        //indent:0 exp:0
interface CorrectAnnotationIndentInterface {                             //indent:0 exp:0
}                                                                        //indent:0 exp:0

@ValidIndentAnno1                                                        //indent:0 exp:0
@ValidIndentAnno2                                                        //indent:0 exp:0
record CorrectAnnotationIndentRecord(int value) {                        //indent:0 exp:0
}                                                                        //indent:0 exp:0

@ValidIndentAnno1                                                        //indent:0 exp:0
@ValidIndentAnno2                                                        //indent:0 exp:0
enum CorrectAnnotationIndentEnum {                                       //indent:0 exp:0
    @ValidIndentAnno1                                                    //indent:4 exp:4
    @ValidIndentAnno2                                                    //indent:4 exp:4
    VALUE1,                                                              //indent:4 exp:4
    VALUE2                                                               //indent:4 exp:4
}                                                                        //indent:0 exp:0

@interface CorrectAnnotationIndentAnno {                                 //indent:0 exp:0
    @ValidIndentAnno1                                                    //indent:4 exp:4
    @ValidIndentAnno2                                                    //indent:4 exp:4
    String value();                                                      //indent:4 exp:4
}                                                                        //indent:0 exp:0

record CorrectAnnotationIndentRecord2(int value) {                       //indent:0 exp:0
    @ValidIndentAnno1                                                    //indent:4 exp:4
    @ValidIndentAnno2                                                    //indent:4 exp:4
    public CorrectAnnotationIndentRecord2 {                              //indent:4 exp:4
    }                                                                    //indent:4 exp:4
}                                                                        //indent:0 exp:0

@Retention(RetentionPolicy.RUNTIME)                                      //indent:0 exp:0
@interface ValidIndentAnno1 {                                            //indent:0 exp:0
}                                                                        //indent:0 exp:0

@Retention(RetentionPolicy.RUNTIME)                                      //indent:0 exp:0
@interface ValidIndentAnno2 {                                            //indent:0 exp:0
}                                                                        //indent:0 exp:0

@Retention(RetentionPolicy.RUNTIME)                                      //indent:0 exp:0
@interface ValidIndentAnno3 {                                            //indent:0 exp:0
    String[] value() default {};                                         //indent:4 exp:4
}                                                                        //indent:0 exp:0

@Retention(RetentionPolicy.RUNTIME)                                      //indent:0 exp:0
@interface ValidIndentAnno4 {                                            //indent:0 exp:0
}                                                                        //indent:0 exp:0

@Retention(RetentionPolicy.RUNTIME)                                      //indent:0 exp:0
@interface ValidIndentAnno5 {                                            //indent:0 exp:0
}                                                                        //indent:0 exp:0
