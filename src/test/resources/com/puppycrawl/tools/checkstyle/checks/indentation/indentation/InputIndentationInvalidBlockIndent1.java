package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * @author  jrichard                                                          //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputIndentationInvalidBlockIndent1 { //indent:0 exp:0

    /** Creates a new instance of InputValidBlockIndent */ //indent:4 exp:4
    public InputIndentationInvalidBlockIndent1() { //indent:4 exp:4
    } //indent:4 exp:4


//  static init at beginning of line is broken for now //indent:0 exp:0


  static { int var = 4; } //indent:2 exp:4 warn
      static { int var = 4; } //indent:6 exp:4 warn


    static { //indent:4 exp:4
       int var = 4;  //indent:7 exp:8 warn
    } //indent:4 exp:4

      static { //indent:6 exp:4 warn
        int var = 4;  //indent:8 exp:8
  } //indent:2 exp:4 warn

  static { //indent:2 exp:4 warn
        int var = 4;  //indent:8 exp:8
      } //indent:6 exp:4 warn

  static  //indent:2 exp:4 warn
    { //indent:4 exp:4
      int var = 4;  //indent:6 exp:8 warn
    } //indent:4 exp:4
    static  //indent:4 exp:4
  { //indent:2 exp:4 warn
      int var = 4;  //indent:6 exp:8 warn
      } //indent:6 exp:4 warn


    static  //indent:4 exp:4
    { //indent:4 exp:4
      int var = 4;  //indent:6 exp:8 warn
    } //indent:4 exp:4

    static  //indent:4 exp:4
    { //indent:4 exp:4
    int var = 4;  //indent:4 exp:8 warn
  } //indent:2 exp:4 warn

    static  //indent:4 exp:4
    { //indent:4 exp:4
        int var = 4;  //indent:8 exp:8
      } //indent:6 exp:4 warn


  { int var = 4; } //indent:2 exp:4 warn
      { int var = 4; } //indent:6 exp:4 warn


  { //indent:2 exp:4 warn
        int var = 4;  //indent:8 exp:8
      } //indent:6 exp:4 warn

      { //indent:6 exp:4 warn
        int var = 4;  //indent:8 exp:8
  } //indent:2 exp:4 warn

    { //indent:4 exp:4
      int var = 4;  //indent:6 exp:8 warn
    } //indent:4 exp:4

} //indent:0 exp:0
