package com.puppycrawl.tools.checkstyle.checks.indentation; //indent:0 exp:0

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
 * @author  jrichard                                                         //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputInvalidSwitchIndent { //indent:0 exp:0

    private static final int CONST = 5; //indent:4 exp:4
    private static final int CONST2 = 2; //indent:4 exp:4
    private static final int CONST3 = 3; //indent:4 exp:4

    /** Creates a new instance of InputInvalidSwitchIndent */ //indent:4 exp:4
    public InputInvalidSwitchIndent() { //indent:4 exp:4
    } //indent:4 exp:4

    private void method1() { //indent:4 exp:4
        int s = 3; //indent:8 exp:8

      switch (s) { //indent:6 exp:8 warn

          case 4: //indent:10 exp:12 warn
              System.out.println(""); //indent:14 exp:16 warn
                break; //indent:16 exp:16

            case CONST: //indent:12 exp:12
              break; //indent:14 exp:16 warn

              case CONST2: //indent:14 exp:12 warn
          case CONST3: //indent:10 exp:12 warn
                break; //indent:16 exp:16

          default: //indent:10 exp:12 warn
              System.out.println(""); //indent:14 exp:16 warn
              break; //indent:14 exp:16 warn
        } //indent:8 exp:8


        // some people like to add curlies to their cases: //indent:8 exp:8
        switch (s) { //indent:8 exp:8

            case 4: { //indent:12 exp:12
              System.out.println(""); //indent:14 exp:16 warn
                  break; //indent:18 exp:16 warn
          } //indent:10 exp:12 warn

            case CONST2: //indent:12 exp:12
            case CONST3: //indent:12 exp:12
          { //indent:10 exp:12 warn
                System.out.println(""); //indent:16 exp:16
                break; //indent:16 exp:16
              } //indent:14 exp:12 warn


            case 22: //indent:12 exp:12
              { //indent:14 exp:12 warn
                System.out.println(""); //indent:16 exp:16
                break; //indent:16 exp:16
          } //indent:10 exp:12 warn
        } //indent:8 exp:8

        // check broken 'case' lines //indent:8 exp:8
        switch (s) { //indent:8 exp:8

            case //indent:12 exp:12
              CONST: //indent:14 exp:16 warn
                break; //indent:16 exp:16

            case CONST2: //indent:12 exp:12
            case //indent:12 exp:12
              CONST3: //indent:14 exp:16 warn
            { //indent:12 exp:12
                System.out.println(""); //indent:16 exp:16
                break; //indent:16 exp:16
            } //indent:12 exp:12
        } //indent:8 exp:8

        switch (s) { //indent:8 exp:8
      } //indent:6 exp:8 warn

        switch (s) //indent:8 exp:8
      { //indent:6 exp:8 warn
          } //indent:10 exp:8 warn
        switch (s) //indent:8 exp:8
          { //indent:10 exp:8 warn
      } //indent:6 exp:8 warn

    } //indent:4 exp:4

} //indent:0 exp:0
