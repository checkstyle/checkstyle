/* Config:                                                                  //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 * arrayInitIndent = 0                                                      //indent:1 exp:1
 * basicOffset = 2                                                          //indent:1 exp:1
 * braceAdjustment = 0                                                      //indent:1 exp:1
 * caseIndent = 4                                                           //indent:1 exp:1
 * forceStrictCondition = false                                             //indent:1 exp:1
 * lineWrappingIndentation = 0                                              //indent:1 exp:1
 * tabWidth = 8                                                             //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 */                                                                         //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

@InputIndentationAnnArrInitWithEmoji.Foo({ //indent:0 exp:0
    @InputIndentationAnnArrInitWithEmoji.Bar, //indent:4 exp:0,41,43 warn
@InputIndentationAnnArrInitWithEmoji.Bar, //indent:0 exp:0
}) //indent:0 exp:0

class InputIndentationAnnArrInitWithEmoji { //indent:0 exp:0
  @interface Foo { //indent:2 exp:2
    Bar[] value() default {}; //indent:4 exp:4

    String[] values() default       {   "Hello👍🤩", "Checkstyle", "🎄"}; //indent:4 exp:4
  } //indent:2 exp:2

  @interface Baz { //indent:2 exp:2
    String[] value() default { //indent:4 exp:4
        "🎄",  "Hello", //indent:8 exp:4,29,31 warn
    "Checkstyle" //indent:4 exp:4
  }; //indent:2 exp:4 warn
  } //indent:2 exp:2

  @interface Bar {} //indent:2 exp:2
} //indent:0 exp:0

interface SomeInterface4 { //indent:0 exp:0
  @interface SomeAnnotation { String[] values(); } //indent:2 exp:2

  interface Info { //indent:2 exp:2
      String A = "🤛🏻a "; //indent:6 exp:4 warn
    String  B = "b 👇🏻"; //indent:4 exp:4
  } //indent:2 exp:2

  @SomeAnnotation(values =  //indent:2 exp:2
      { //indent:6 exp:2 warn
          "d😆🤛🏻", //indent:10 exp:2,6,8 warn
            "👆🏻👇🏻", //indent:12 exp:2,6,8 warn
                    "😂", "  ", "😂🎄", //indent:20 exp:2,6,8 warn
  Info.A, //indent:2 exp:2
    Info.B //indent:4 exp:2,6,8 warn
  } //indent:2 exp:2
  ) //indent:2 exp:2
  void works(); //indent:2 exp:2
} //indent:0 exp:0
