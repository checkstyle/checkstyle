package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;             //indent:0 exp:0

public class InputIndentationMethodPrecededByAnnotationWithParameterOnSeparateLine {//indent:0 exp:0

  @interface  Annotation1 {                                                         //indent:2 exp:2
    String field1();                                                                //indent:4 exp:4
    String field2();                                                                //indent:4 exp:4
  }                                                                                 //indent:2 exp:2

  @interface Annotation2 {};                                                        //indent:2 exp:2

  @Annotation1(field1 = "foo", field2 = "bar")                                      //indent:2 exp:2
  public @Annotation2 String method(                                                //indent:2 exp:2
      String param                                                                  //indent:6 exp:6
  ) {                                                                               //indent:2 exp:2
    return null;                                                                    //indent:4 exp:4
  }                                                                                 //indent:2 exp:2

}                                                                                   //indent:0 exp:0

