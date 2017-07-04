package com.puppycrawl.tools.checkstyle.checks.indentation; //indent:0 exp:0

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
public class InputBraceAdjustment //indent:0 exp:0
  { //indent:2 exp:2

    /** Creates a new instance of InputBraceAdjustment */ //indent:4 exp:4
    public InputBraceAdjustment() //indent:4 exp:4
      { //indent:6 exp:6
        // sorry about the religious commentary... :) //indent:8 exp:8
        boolean uglyGnuStyle = true; //indent:8 exp:8
        if (uglyGnuStyle) //indent:8 exp:8
          { //indent:10 exp:10
            System.identityHashCode("ugly GNU style braces"); //indent:12 exp:12
        } //indent:8 exp:10 warn
      } //indent:6 exp:6

  } //indent:2 exp:2
