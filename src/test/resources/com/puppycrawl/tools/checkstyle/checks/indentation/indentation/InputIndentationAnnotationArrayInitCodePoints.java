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

@𝒜({ "first",                                                   //indent:0 exp:0
     "second"})                                                 //indent:5 exp:5
class AnnotationArrayExample {                                  //indent:0 exp:0
}                                                               //indent:0 exp:0

@𝒜({ "first",                                                   //indent:0 exp:0
    "second"})                                                  //indent:4 exp:0,3,5 warn
class IncorrectAnnotationArray {                                //indent:0 exp:0
}                                                               //indent:0 exp:0

@Α({ "first",                                                   //indent:0 exp:0
     "second"})                                                 //indent:5 exp:5
class BmpAnnotationArray {                                      //indent:0 exp:0
}                                                               //indent:0 exp:0

@interface 𝒜 {                                                  //indent:0 exp:0
  String[] value();                                             //indent:2 exp:2
}                                                               //indent:0 exp:0

@interface Α {                                                  //indent:0 exp:0
  String[] value();                                             //indent:2 exp:2
}                                                               //indent:0 exp:0
