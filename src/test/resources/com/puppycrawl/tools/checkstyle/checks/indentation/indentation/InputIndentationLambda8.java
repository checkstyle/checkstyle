/* Config:                                                                    //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 3                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 7                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
//a comment //indent:0 exp:0

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationLambda8 { //indent:0 exp:0
   void function1(Runnable x) { //indent:3 exp:3
      Runnable r1 = //indent:6 exp:6
                    () -> { //indent:20 exp:20
             }; //indent:13 exp:13
      Runnable r2 = () -> { x.run(); }; //indent:6 exp:6
   } //indent:3 exp:3
} //indent:0 exp:0
// ok //indent:0 exp:0
