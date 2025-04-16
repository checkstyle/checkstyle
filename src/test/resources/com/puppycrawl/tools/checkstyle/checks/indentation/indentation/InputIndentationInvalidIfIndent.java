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
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputIndentationInvalidIfIndent { //indent:0 exp:0

    // ctor with rcurly on same line //indent:4 exp:4
    public InputIndentationInvalidIfIndent() { //indent:4 exp:4
    } //indent:4 exp:4

    // ctor with rcurly on next line //indent:4 exp:4
    public InputIndentationInvalidIfIndent(int dummy) //indent:4 exp:4
    { //indent:4 exp:4
    } //indent:4 exp:4

    // method with rcurly on same line //indent:4 exp:4
    public void method() { //indent:4 exp:4
    } //indent:4 exp:4

    // method with rcurly on next line //indent:4 exp:4
    public void method2() //indent:4 exp:4
    { //indent:4 exp:4
    } //indent:4 exp:4

    // method with a bunch of params //indent:4 exp:4
    public void method2(int x, int y, int w, int h) //indent:4 exp:4
    { //indent:4 exp:4
    } //indent:4 exp:4

    // params on multiple lines //indent:4 exp:4
    public void method2(int x, int y, int w, int h, //indent:4 exp:4
        int x1, int y1, int w1, int h1) //indent:8 exp:>=8
    { //indent:4 exp:4
    } //indent:4 exp:4

    // test ifs //indent:4 exp:4
    public void emptyIfTest() //indent:4 exp:4
    { //indent:4 exp:4
        boolean test = true; //indent:8 exp:8

        // lcurly on same line //indent:8 exp:8
 if (test) { //indent:1 exp:8 warn
        } //indent:8 exp:8

        // lcurly on next line-- if, rcurly indented too far, lcurly not far enough //indent:8 exp:8
        //  //indent:8 exp:8
         if (test)  //indent:9 exp:8 warn
         { //indent:9 exp:8 warn
       } //indent:7 exp:8 warn

      if (test)  //indent:6 exp:8 warn
     { //indent:5 exp:8 warn
     } //indent:5 exp:8 warn

        //lcurly for if and else on same line, much space after if on same line - ok//indent:8 exp:8
        if (test)      { //indent:8 exp:8
          } else {      // this is not allowed //indent:10 exp:8 warn
       } //indent:7 exp:8 warn

        // lcurly for if and else on same line //indent:8 exp:8
         if (test)  //indent:9 exp:8 warn
       { //indent:7 exp:8 warn
        }  //indent:8 exp:8
         else  //indent:9 exp:8 warn
        { //indent:8 exp:8
         } //indent:9 exp:8 warn

        // lcurly for if and else on same line -- mixed braces //indent:8 exp:8
          if (test) { //indent:10 exp:8 warn
       }  //indent:7 exp:8 warn
         else  //indent:9 exp:8 warn
       { //indent:7 exp:8 warn
         } //indent:9 exp:8 warn


        // lcurly for if and else on same line -- mixed braces //indent:8 exp:8
         if (test)  //indent:9 exp:8 warn
         { //indent:9 exp:8 warn
         } else  //indent:9 exp:8 warn
       { //indent:7 exp:8 warn
          } //indent:10 exp:8 warn

        // lcurly for if and else on same line -- mixed braces //indent:8 exp:8
      if (test)  //indent:6 exp:8 warn
          { //indent:10 exp:8 warn
          } else { //indent:10 exp:8 warn
       } //indent:7 exp:8 warn

        // lcurly for if and else on same line -- mixed braces, unnested //indent:8 exp:8
     if (test) { //indent:5 exp:8 warn
           }  //indent:11 exp:8 warn
     else { //indent:5 exp:8 warn
           } //indent:11 exp:8 warn
    } //indent:4 exp:4

} //indent:0 exp:0
