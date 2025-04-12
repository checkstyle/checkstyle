package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.lang.annotation.ElementType; //indent:0 exp:0
import java.lang.annotation.Repeatable; //indent:0 exp:0
import java.lang.annotation.Retention; //indent:0 exp:0
import java.lang.annotation.RetentionPolicy; //indent:0 exp:0
import java.lang.annotation.Target; //indent:0 exp:0

public @interface InputIndentationCustomAnnotation { //indent:0 exp:0
    int value = 1; //indent:4 exp:4
    int value() default 0; //indent:4 exp:4
} //indent:0 exp:0

     @Retention(RetentionPolicy.SOURCE) @interface AnnotationWithComments { // //indent:5 exp:0 warn
     } //indent:5 exp:0 warn
     @interface AnnotationWithStringProperty { //indent:5 exp:0 warn
String author(); //indent:0 exp:4 warn
     } //indent:5 exp:0 warn

   @Retention(RetentionPolicy.SOURCE) //indent:3 exp:0 warn
@interface AnnotationWithDefaultStringAndAnnotation { //indent:0 exp:0
String author() default "author"; //indent:0 exp:4 warn
     } //indent:5 exp:0 warn

     @interface //indent:5 exp:0 warn
     AnnotationWithLineWrap { //indent:5 exp:0 warn
    String author(); //indent:4 exp:4
} //indent:0 exp:0

     @Target(ElementType.METHOD) //indent:5 exp:0 warn
   @Retention(RetentionPolicy.RUNTIME) //indent:3 exp:0 warn
@interface AnnotationWithTarget { //indent:0 exp:0
    String author() default "author"; //indent:4 exp:4
     String book(); //indent:5 exp:4 warn
   @Retention(RetentionPolicy.SOURCE) //indent:3 exp:4 warn
@Repeatable(RepeatableInner.class) //indent:0 exp:4 warn
@interface //indent:0 exp:4 warn
        AnnotationInnerLineWrap //indent:8 exp:4 warn
    { //indent:4 exp:4
        public String author() default "auth"; //indent:8 exp:8
       @AnnotationWithTarget(book = "") public String title() default "title"; //indent:7 exp:8 warn
    }//indent:4 exp:4
} //indent:0 exp:0
@interface RepeatableInner { //indent:0 exp:0
    AnnotationWithTarget.AnnotationInnerLineWrap[] value(); //indent:4 exp:4
} //indent:0 exp:0

@interface Ann {} //indent:0 exp:0

class InnerAnonClass { //indent:0 exp:0
    @interface MyInnerAnno { //indent:4 exp:4
        String author(); //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0
@Retention(RetentionPolicy.SOURCE) //indent:0 exp:0
@Repeatable(RepeatableInner2.class) //indent:0 exp:0
@interface //indent:0 exp:0
    AnnotationInnerLineWrap2 { //indent:4 exp:0 warn
   public String author() default "auth"; //indent:3 exp:4 warn
       @AnnotationWithTarget(book = "") public String title() default "title"; //indent:7 exp:4 warn
    }//indent:4 exp:0 warn

@interface RepeatableInner2 { //indent:0 exp:0
    AnnotationInnerLineWrap2[] value(); //indent:4 exp:4
} //indent:0 exp:0

@interface RepeatableInner3 { //indent:0 exp:0
    AnnotationInnerLineWrap2[] value(); //indent:4 exp:4
} //indent:0 exp:0

class InnerAnnotSingleLine { //indent:0 exp:0
   @Retention(RetentionPolicy.SOURCE) @interface AnnotationOneLine {} //indent:3 exp:4 warn
} //indent:0 exp:0

@interface AnnotationDef { //indent:0 exp:0
    int value = 1; //indent:4 exp:4
} //indent:0 exp:0

@interface AnnotationDef2 { //indent:0 exp:0
    int value //indent:4 exp:4
        = Integer.parseInt("1"); //indent:8 exp:8
    Object object = new Object(){ //indent:4 exp:4
        @Override //indent:8 exp:8
        public String toString() { //indent:8 exp:8
            return //indent:12 exp:12
                    new String( //indent:20 exp:20
                            new StringBuilder("Hello") //indent:28 exp:20,24 warn
                                    .append(",World")) //indent:36 exp:36
                    ; //indent:20 exp:20
        } //indent:8 exp:8
    }.toString(); //indent:4 exp:4
} //indent:0 exp:0
