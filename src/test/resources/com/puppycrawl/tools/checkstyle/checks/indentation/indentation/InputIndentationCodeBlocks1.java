/* Config:                                                                  //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 * basicOffset = 2                                                          //indent:1 exp:1
 * braceAdjustment = 2                                                      //indent:1 exp:1
 * caseIndent = 2                                                           //indent:1 exp:1
 * lineWrappingIndentation = 4                                              //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 */                                                                         //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationCodeBlocks1 { //indent:0 exp:0
  { //indent:2 exp:2
    System.out.println("True"); //indent:4 exp:4
  } //indent:2 exp:2
    { //indent:4 exp:2 warn
      System.out.println("False"); //indent:6 exp:4 warn
    } //indent:4 exp:2 warn
  private static void test(int condition) { //indent:2 exp:2
    { //indent:4 exp:4
      System.out.println("True"); //indent:6 exp:6
    } //indent:4 exp:4
  } //indent:2 exp:2

  { //indent:2 exp:2
    System.out.println("False"); //indent:4 exp:4
  } //indent:2 exp:2

    { //indent:4 exp:2 warn
      System.out.println("False"); //indent:6 exp:4 warn
    } //indent:4 exp:2 warn

  public void testSwitchCases(int j) { //indent:2 exp:2
    switch (j) { //indent:4 exp:4
      case 1: //indent:6 exp:6
        { //indent:8 exp:8
          System.out.println("One"); //indent:10 exp:10
          break; //indent:10 exp:10
        } //indent:8 exp:8
      case 2: //indent:6 exp:6
        { //indent:8 exp:8
          test(2); //indent:10 exp:10
          break; //indent:10 exp:10
        } //indent:8 exp:8
      default: //indent:6 exp:6
        System.out.println("default"); //indent:8 exp:8
    } //indent:4 exp:4
  } //indent:2 exp:2

  class Inner { //indent:2 exp:2
    void test() { //indent:4 exp:4
      { //indent:6 exp:6
        System.out.println("True"); //indent:8 exp:8
      } //indent:6 exp:6
    } //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0
