/* Config:                                                                  //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 * arrayInitIndent = 6                                                      //indent:1 exp:1
 * basicOffset = 2                                                          //indent:1 exp:1
 * braceAdjustment = 0                                                      //indent:1 exp:1
 * caseIndent = 4                                                           //indent:1 exp:1
 * forceStrictCondition = false                                             //indent:1 exp:1
 * lineWrappingIndentation = 4                                              //indent:1 exp:1
 * tabWidth = 8                                                             //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 */                                                                         //indent:1 exp:1
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

@InputIndentationAnnArrInit.Foo({ @InputIndentationAnnArrInit.Bar, //indent:0 exp:0
    @InputIndentationAnnArrInit.Bar, //indent:4 exp:4
@InputIndentationAnnArrInit.Bar, //indent:0 exp:4,6,34,36 warn
}) //indent:0 exp:0

@InputIndentationAnnArrInit.Baz({ //indent:0 exp:0
    "Hello", //indent:4 exp:4
             "Checkstyle", //indent:13 exp:4,6,34,36 warn
  }) //indent:2 exp:0,4 warn

class InputIndentationAnnArrInit { //indent:0 exp:0
  @interface Foo { //indent:2 exp:2
    Bar[] value() default {}; //indent:4 exp:4

    String[] values() default {"Hello", "Checkstyle"}; //indent:4 exp:4
  } //indent:2 exp:2

  @interface Baz { //indent:2 exp:2
    String[] value() default { //indent:4 exp:4
        "Hello", //indent:8 exp:8
      "Checkstyle" //indent:6 exp:8,10,31,33 warn
  }; //indent:2 exp:4,8 warn
  } //indent:2 exp:2

  @interface Bar {} //indent:2 exp:2
} //indent:0 exp:0

interface SomeInterface { //indent:0 exp:0
  @interface SomeAnnotation { String[] values(); } //indent:2 exp:2

  interface Info { //indent:2 exp:2
    String A = "a"; //indent:4 exp:4
    String B = "b"; //indent:4 exp:4
  } //indent:2 exp:2

  @SomeAnnotation(values =  //indent:2 exp:2
      { //indent:6 exp:6
     Info.A, //indent:5 exp:6,8,10 warn
      Info.B //indent:6 exp:6
     } //indent:5 exp:2,6 warn
  ) //indent:2 exp:2
  void works(); //indent:2 exp:2
} //indent:0 exp:0
