package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;     //indent:0 exp:0

/* Config:                                                                  //indent:0 exp:0
 * basicOffset = 2                                                          //indent:1 exp:1
 * braceAdjustment = 2                                                      //indent:1 exp:1
 * caseIndent = 2                                                           //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 * lineWrappingIndentation = 4                                              //indent:1 exp:1
 * arrayInitIndent = 2                                                      //indent:1 exp:1
 */                                                                         //indent:1 exp:1

@InputIndentationAnnotationArray.AnnotList({                                //indent:0 exp:0
    @InputIndentationAnnotationArray.Annot(                                 //indent:4 exp:4
      "hello"                                                               //indent:6 exp:6
    ),                                                                      //indent:4 exp:4
    @InputIndentationAnnotationArray.Annot(                                 //indent:4 exp:4
      "world"                                                               //indent:6 exp:6
    ),                                                                      //indent:4 exp:4
  @InputIndentationAnnotationArray.Annot(                                   //indent:2 exp:2
      "lineWrappingIndenation"                                              //indent:6 exp:6
  )                                                                         //indent:2 exp:2
})                                                                          //indent:0 exp:0
public class InputIndentationAnnotationArray {                              //indent:0 exp:0
  int testMethod1(int val) {                                                //indent:2 exp:2
    return val+1;                                                           //indent:4 exp:4
  }                                                                         //indent:2 exp:2
  @interface Annot {                                                        //indent:2 exp:2
    String value() default "";                                              //indent:4 exp:4

    String[] values() default {"Hello", "Checkstyle"};                      //indent:4 exp:4
  }                                                                         //indent:2 exp:2
  @interface AnnotList {                                                    //indent:2 exp:2
    InputIndentationAnnotationArray.Annot[] value();                        //indent:4 exp:4
  }                                                                         //indent:2 exp:2
}                                                                           //indent:0 exp:0
