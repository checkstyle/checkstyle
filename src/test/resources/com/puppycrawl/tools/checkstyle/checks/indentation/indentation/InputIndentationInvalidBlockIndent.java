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
public class InputIndentationInvalidBlockIndent { //indent:0 exp:0

    /** Creates a new instance of InputValidBlockIndent */ //indent:4 exp:4
    public InputIndentationInvalidBlockIndent() { //indent:4 exp:4
    } //indent:4 exp:4

    public void method1() { //indent:4 exp:4

        { } //indent:8 exp:8
       { } //indent:7 exp:8 warn
         { } //indent:9 exp:8 warn

         { //indent:9 exp:8 warn
       } //indent:7 exp:8 warn

      { //indent:6 exp:8 warn

      } //indent:6 exp:8 warn
      { //indent:6 exp:8 warn
        } //indent:8 exp:8

         { //indent:9 exp:8 warn
             int var = 3; //indent:13 exp:12 warn

             var += 3; //indent:13 exp:12 warn
         } //indent:9 exp:8 warn


      { //indent:6 exp:8 warn
          int var = 3; //indent:10 exp:12 warn

          var += 3; //indent:10 exp:12 warn
      } //indent:6 exp:8 warn


      {  int var = 5; } //indent:6 exp:8 warn

        { //indent:8 exp:8
          int var = 3; //indent:10 exp:12 warn

            var += 3; //indent:12 exp:12

          { //indent:10 exp:12 warn
                int innerVar = 4; //indent:16 exp:16

                innerVar += var; //indent:16 exp:16
          } //indent:10 exp:12 warn
        } //indent:8 exp:8
        { //indent:8 exp:8
            int var = 3; //indent:12 exp:12

          var += 3; //indent:10 exp:12 warn

          { //indent:10 exp:12 warn
              int innerVar = 4; //indent:14 exp:16 warn

                innerVar += var; //indent:16 exp:16
            } //indent:12 exp:12
        } //indent:8 exp:8

        { //indent:8 exp:8
            int var = 3; //indent:12 exp:12

            var += 3; //indent:12 exp:12

            { //indent:12 exp:12
                int innerVar = 4; //indent:16 exp:16

                innerVar += var; //indent:16 exp:16
          } //indent:10 exp:12 warn
        } //indent:8 exp:8

    } //indent:4 exp:4

} //indent:0 exp:0
