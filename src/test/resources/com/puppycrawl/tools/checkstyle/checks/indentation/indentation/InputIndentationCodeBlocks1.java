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

  class Inner { //indent:2 exp:2
    void test() { //indent:4 exp:4
      { //indent:6 exp:6
        System.out.println("True"); //indent:8 exp:8
      } //indent:6 exp:6
    } //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0
