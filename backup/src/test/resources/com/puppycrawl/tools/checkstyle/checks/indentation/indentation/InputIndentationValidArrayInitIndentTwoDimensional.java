/* Config:                                                                  //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 * arrayInitIndent = 2                                                      //indent:1 exp:1
 * basicOffset = 4                                                          //indent:1 exp:1
 * braceAdjustment = 4                                                      //indent:1 exp:1
 * caseIndent = 4                                                           //indent:1 exp:1
 * forceStrictCondition = false                                             //indent:1 exp:1
 * lineWrappingIndentation = 4                                              //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 */                                                                         //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationValidArrayInitIndentTwoDimensional { //indent:0 exp:0
    int[][] a =  { //indent:4 exp:4
      new int[] {1, 2}, //indent:6 exp:6
      {1, 2}, //indent:6 exp:6
    }; //indent:4 exp:4

    int[][] b = { //indent:4 exp:4
      { 1, //indent:6 exp:6
        2, //indent:8 exp:8
        3, //indent:8 exp:8
      }, //indent:6 exp:6
      {5, 6, 7} //indent:6 exp:6
    }; //indent:4 exp:4
} //indent:0 exp:0
