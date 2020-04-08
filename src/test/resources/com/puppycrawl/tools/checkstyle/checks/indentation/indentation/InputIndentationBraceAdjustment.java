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
  { //indent:2 exp:2

    /** Creates a new instance of InputIndentationBraceAdjustment */ //indent:4 exp:4
    public InputIndentationBraceAdjustment() //indent:4 exp:4
      { //indent:6 exp:6
        // sorry about the religious commentary... :) //indent:8 exp:8
        boolean uglyGnuStyle = true; //indent:8 exp:10 warn
        if (uglyGnuStyle) //indent:8 exp:10 warn
          { //indent:10 exp:12 warn
            System.identityHashCode("ugly GNU style braces"); //indent:12 exp:14,16 warn
        } //indent:8 exp:12 warn
      } //indent:6 exp:6

  } //indent:2 exp:2
