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
public class InputIndentationInvalidIfIndent2 { //indent:0 exp:0

    /////  same as above, with statements //indent:4 exp:4
    public void populatedIfTest() //indent:4 exp:4
    { //indent:4 exp:4
        boolean test = false; //indent:8 exp:8

        if (test //indent:8 exp:8
         && 7 < 8 && 8 < 9 //indent:9 exp:12 warn
           && 10 < 11) { //indent:11 exp:12 warn
        } //indent:8 exp:8

        if (test) //indent:8 exp:8
          return; //indent:10 exp:12 warn

        if (test) { //indent:8 exp:8
       } else if (7 < 8) { //indent:7 exp:8 warn
        } else if (8 < 9) { //indent:8 exp:8
        } //indent:8 exp:8

        if (test) { //indent:8 exp:8
            System.getProperty("blah");  //indent:12 exp:>=12
        } else if (7 < 8) { //indent:8 exp:8
          System.getProperty("blah");  //indent:10 exp:>=12 warn
        } else if (8 < 9) { //indent:8 exp:8
          System.getProperty("blah");  //indent:10 exp:>=12 warn
        } //indent:8 exp:8


        if (test) //indent:8 exp:8
            System.getProperty("blah");  //indent:12 exp:12
        else if (7 < 8) //indent:8 exp:8
          System.getProperty("blah");  //indent:10 exp:12 warn
        else if (8 < 9) //indent:8 exp:8
            System.getProperty("blah");  //indent:12 exp:12


        //                               //indent:8 exp:8
        if (test) { //indent:8 exp:8
            System.getProperty("blah");  //indent:12 exp:12
        } else  //indent:8 exp:8
          if (7 < 8) { //indent:10 exp:12 warn
                System.getProperty("blah");  //indent:16 exp:16
            } else  //indent:12 exp:12
                if (8 < 9) { //indent:16 exp:16
                  System.getProperty("blah");  //indent:18 exp:20 warn
                } //indent:16 exp:16

        if (test) { //indent:8 exp:8
            System.getProperty("blah"); } //indent:12 exp:12
    } //indent:4 exp:4

    public void parenIfTest() { //indent:4 exp:4
        boolean test = true; //indent:8 exp:8

        if (test //indent:8 exp:8
          ) { //indent:10 exp:8 warn
            System.getProperty("blah");  //indent:12 exp:12
        } //indent:8 exp:8

        if (test //indent:8 exp:8
      )  //indent:6 exp:8 warn
        { //indent:8 exp:8
            System.getProperty("blah");  //indent:12 exp:12
        } //indent:8 exp:8

        if  //indent:8 exp:8
      ( //indent:6 exp:8 warn
            test //indent:12 exp:12
      ) { //indent:6 exp:8 warn
            System.getProperty("blah");  //indent:12 exp:12
        } //indent:8 exp:8
if (test  //indent:0 exp:8 warn
|| test) {  //indent:0 exp:12 warn
System.getProperty("blah");  //indent:0 exp:12 warn
}  //indent:0 exp:8 warn
if (test) //indent:0 exp:8 warn
System.getProperty("blah"); //indent:0 exp:12 warn
else //indent:0 exp:8 warn
System.getProperty("blah"); //indent:0 exp:12 warn

    } //indent:4 exp:4

} //indent:0 exp:0
