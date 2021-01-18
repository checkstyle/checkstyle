/* Config:                                                                    //indent:0 exp:0
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 2                                                        //indent:1 exp:1
 * basicOffset = 2                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 2                                                             //indent:1 exp:1
 * forceStrictCondition = true                                                //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 2                                                               //indent:1 exp:1
 * throwsIndent = 2                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public enum InputIndentationInvalidEnumStrictCondition { //indent:0 exp:0

  MY_VALUE("first line" //indent:2 exp:2
        + "second line" //indent:8 exp:6 warn
                     + "third line"), //indent:21 exp:6 warn

   MY_VALUE_2("first line" //indent:3 exp:2 warn
      + "second line"), //indent:6 exp:6

  @Deprecated //indent:2 exp:2
  MY_VALUE_3("first line" //indent:2 exp:2
      + "second line"), //indent:6 exp:6

  @Deprecated //indent:2 exp:2
    @EnumAnotation //indent:4 exp:2 warn
  MY_VALUE_4("first line" //indent:2 exp:2
      + "second line"), //indent:6 exp:6

   @Deprecated //indent:3 exp:2 warn
  MY_VALUE_5("first line" //indent:2 exp:2
      + "second line"), //indent:6 exp:6

  MY_VALUE_6("first line" //indent:2 exp:2
      + "second line" //indent:6 exp:6
  ); //indent:2 exp:2

  private final String message; //indent:2 exp:2

  InputIndentationInvalidEnumStrictCondition(final String message) { //indent:2 exp:2
    this.message = message; //indent:4 exp:4
  } //indent:2 exp:2

  @Deprecated //indent:2 exp:2
    public String getMessage() { //indent:4 exp:2 warn
    return message; //indent:4 exp:4
  } //indent:2 exp:2

  public @interface EnumAnotation { //indent:2 exp:2
  } //indent:2 exp:2

  private enum SampleEnum { A, B, C } //indent:2 exp:2

   private enum SampleEnum2 { A, B, C } //indent:3 exp:2 warn
} //indent:0 exp:0
