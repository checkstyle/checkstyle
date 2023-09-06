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

public class InputIndentationInvalidArrayInitIndentTwoDimensional { //indent:0 exp:0
    int[][] a = { //indent:4 exp:4
      {1, 2, 3, 4 ,5 ,6 , 7}, //indent:6 exp:6
    {1, 2, 3, 34, 4,} //indent:4 exp:6,8,18,20,24 warn
    }; //indent:4 exp:4

    int[][] c = { //indent:4 exp:4
      { //indent:6 exp:6
         1, 2, 3 , 34, //indent:9 exp:8,10,12,20,22,24 warn
      }, //indent:6 exp:6
      { //indent:6 exp:6
      1, 2, 2, 3 //indent:6 exp:8,10,12,20,22,24 warn
      }, //indent:6 exp:6
    { //indent:4 exp:6,8,18,20,24 warn
      1, 2, 3 ,4 //indent:6 exp:6
    } //indent:4 exp:6,8,18,20,24 warn
    }; //indent:4 exp:4
} //indent:0 exp:0
