package com.google.checkstyle.test.chapter4formatting.rule4841indentation; //indent:0 exp:0

public class InputIndentationCorrectAnnotationArrayInit { //indent:0 exp:0
  interface MyInterface { //indent:2 exp:2
    @interface AnAnnotation { String[] values(); } //indent:4 exp:4
    @AnAnnotation(values = { //indent:4 exp:4
      "Hello"//indent:6 exp:6
    }) //indent:4 exp:4
    void works(); //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0
