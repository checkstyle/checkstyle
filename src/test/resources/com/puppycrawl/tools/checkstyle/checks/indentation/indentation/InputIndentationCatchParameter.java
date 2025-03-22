/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 2                                                        //indent:1 exp:1
 * basicOffset = 2                                                            //indent:1 exp:1
 * braceAdjustment = 2                                                        //indent:1 exp:1
 * caseIndent = 2                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */ //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationCatchParameter { //indent:0 exp:0
  void test1() { //indent:2 exp:2
    try { //indent:4 exp:4
      System.out.println("try"); //indent:6 exp:6
    } catch ( //indent:4 exp:4
        @SuppressWarnings("PMD.AvoidCatchingGenericException") //indent:8 exp:8
    Exception e) { //indent:4 exp:8 warn
      java.util.logging.Logger.getAnonymousLogger().severe(e.toString()); //indent:6 exp:6
    } //indent:4 exp:4
  } //indent:2 exp:2

  void test2() { //indent:2 exp:2
    try { //indent:4 exp:4
      System.out.println("try"); //indent:6 exp:6
    } catch ( //indent:4 exp:4
        Exception e) { //indent:8 exp:8
      java.util.logging.Logger.getAnonymousLogger().severe(e.toString()); //indent:6 exp:6
    } //indent:4 exp:4
  } //indent:2 exp:2
} //indent:0 exp:0
