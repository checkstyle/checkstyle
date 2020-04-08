package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 2                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * @author  jrichard                                                          //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputIndentationBraceAdjustment //indent:0 exp:0
{ //indent:0 exp:2 warn

      /** Creates a new instance of InputIndentationBraceAdjustment */ //indent:6 exp:6
  public InputIndentationBraceAdjustment() //indent:2 exp:4,6 warn
  { //indent:2 exp:6,8 warn
            // sorry about the religious commentary... :) //indent:12 exp:12
          boolean uglyGnuStyle = true; //indent:10 exp:10
          if (uglyGnuStyle) //indent:10 exp:10
          { //indent:10 exp:8,12 warn
            System.identityHashCode("ugly GNU style braces"); //indent:12 exp:12
          } //indent:10 exp:8,12 warn
  } //indent:2 exp:6,8 warn

} //indent:0 exp:2 warn

class InputBraceAdjustmentsNewTests //indent:0 exp:0
{ //indent:0 exp:2 warn
    void test() //indent:4 exp:4
    { //indent:4 exp:6,8 warn
        } //indent:8 exp:8
    void test2() { //indent:4 exp:4
    } //indent:4 exp:4
} //indent:0 exp:2 warn
