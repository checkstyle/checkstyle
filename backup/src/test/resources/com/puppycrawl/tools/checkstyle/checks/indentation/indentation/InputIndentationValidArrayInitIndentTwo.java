/* Config:                                                                  //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 * arrayInitIndent = 2                                                      //indent:1 exp:1
 * basicOffset = 2                                                          //indent:1 exp:1
 * braceAdjustment = 2                                                      //indent:1 exp:1
 * caseIndent = 2                                                           //indent:1 exp:1
 * forceStrictCondition = false                                             //indent:1 exp:1
 * lineWrappingIndentation = 4                                              //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 */                                                                         //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationValidArrayInitIndentTwo { //indent:0 exp:0
  private static final String[] UNSECURED_PATHS = { //indent:2 exp:2
    "/health", //indent:4 exp:4
    "/version", //indent:4 exp:4
  }; //indent:2 exp:2

  int[] array3 = new int[] { //indent:2 exp:2
    1, //indent:4 exp:4
    2, //indent:4 exp:4
    3 //indent:4 exp:4
  }; //indent:2 exp:2

  void test() { //indent:2 exp:2
    int[] array3 = new int[] { //indent:4 exp:4
      1, //indent:6 exp:6
      2, //indent:6 exp:6
      3 //indent:6 exp:6
    }; //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0
