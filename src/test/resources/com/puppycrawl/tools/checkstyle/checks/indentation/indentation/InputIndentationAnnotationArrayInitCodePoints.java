/* Config:                                                      //indent:0 exp:0
 * arrayInitIndent = 0                                          //indent:1 exp:1
 * basicOffset = 2                                              //indent:1 exp:1
 * braceAdjustment = 0                                          //indent:1 exp:1
 * caseIndent = 4                                               //indent:1 exp:1
 * forceStrictCondition = false                                 //indent:1 exp:1
 * lineWrappingIndentation = 0                                  //indent:1 exp:1
 * tabWidth = 4                                                 //indent:1 exp:1
 * throwsIndent = 4                                             //indent:1 exp:1
 */                                                             //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationAnnotationArrayInitCodePoints {       //indent:0 exp:0

  @𝒜({ "first",                                                 //indent:2 exp:2
       "second"})                                               //indent:7 exp:7
  class AnnotationArrayExample {                                //indent:2 exp:2
  }                                                             //indent:2 exp:2

  @𝒜({ "first",                                                 //indent:2 exp:2
      "second"})                                                //indent:6 exp:2,5,7 warn
  class IncorrectAnnotationArray {                              //indent:2 exp:2
  }                                                             //indent:2 exp:2

  @Α({ "first",                                                 //indent:2 exp:2
       "second"})                                               //indent:7 exp:7
  class BmpAnnotationArray {                                    //indent:2 exp:2
  }                                                             //indent:2 exp:2

  @interface 𝒜 {                                                //indent:2 exp:2
    String[] value();                                           //indent:4 exp:4
  }                                                             //indent:2 exp:2

  @interface Α {                                                //indent:2 exp:2
    String[] value();                                           //indent:4 exp:4
  }                                                             //indent:2 exp:2
}                                                               //indent:0 exp:0

