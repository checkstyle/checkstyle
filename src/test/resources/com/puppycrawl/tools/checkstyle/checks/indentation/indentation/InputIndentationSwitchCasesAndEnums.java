/* Config:                                                                    //indent:0 exp:0
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 2                                                            //indent:1 exp:1
 * braceAdjustment = 2                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationSwitchCasesAndEnums { //indent:0 exp:0
  { //indent:2 exp:2
      System.out.println("Hello Checks"); //indent:6 exp:4 warn
  } //indent:2 exp:2

  public static void test() { //indent:2 exp:2
    Test myTest = Test.ONE; //indent:4 exp:4
    switch (myTest) { //indent:4 exp:4
      case ONE: //indent:6 exp:6
        { //indent:8 exp:8
          System.out.println("One"); //indent:10 exp:10
          break; //indent:10 exp:10
        } //indent:8 exp:8
      case TWO: //indent:6 exp:6
        { //indent:8 exp:8
          System.out.println("Two"); //indent:10 exp:10
          break; //indent:10 exp:10
        } //indent:8 exp:8
      case THREE: //indent:6 exp:6
      { //indent:6 exp:8 warn
          System.out.println("Three"); //indent:10 exp:10
          break; //indent:10 exp:10
          } //indent:10 exp:8 warn
      case FOUR: { //indent:6 exp:6
        System.out.println("FOur with different brace style"); //indent:8 exp:8
      } //indent:6 exp:6
      default: //indent:6 exp:6
        throw new RuntimeException("Unexpected value"); //indent:8 exp:8
    } //indent:4 exp:4
  } //indent:2 exp:2

  public enum Test { //indent:2 exp:2
    ONE, //indent:4 exp:4
    TWO, //indent:4 exp:4
    THREE, //indent:4 exp:4
    FOUR //indent:4 exp:4
  } //indent:2 exp:2

    { //indent:4 exp:2 warn
  System.out.println("Hello Checks"); //indent:2 exp:4 warn
  } //indent:2 exp:2
} //indent:0 exp:0

