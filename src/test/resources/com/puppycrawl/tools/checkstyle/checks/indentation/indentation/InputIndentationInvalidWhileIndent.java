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
 * @author  jrichard                                                         //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputIndentationInvalidWhileIndent { //indent:0 exp:0

    /** Creates a new instance of InputIndentationValidWhileIndent */ //indent:4 exp:4
    public InputIndentationInvalidWhileIndent() { //indent:4 exp:4
    } //indent:4 exp:4
    private void method1() //indent:4 exp:4
    { //indent:4 exp:4
        boolean test = true; //indent:8 exp:8
         while (test) { //indent:9 exp:8 warn
       } //indent:7 exp:8 warn

       while (test)  //indent:7 exp:8 warn
         { //indent:9 exp:8 warn
         } //indent:9 exp:8 warn

         while (test)  //indent:9 exp:8 warn
      { //indent:6 exp:8 warn
              System.getProperty("foo"); //indent:14 exp:12 warn
      } //indent:6 exp:8 warn

          while (test)  { //indent:10 exp:8 warn
            System.getProperty("foo"); //indent:12 exp:12
          } //indent:10 exp:8 warn

          while (test)  { //indent:10 exp:8 warn
            System.getProperty("foo"); //indent:12 exp:12
            System.getProperty("foo"); //indent:12 exp:12
          } //indent:10 exp:8 warn

      while (test)   //indent:6 exp:8 warn
          { //indent:10 exp:8 warn
            System.getProperty("foo"); //indent:12 exp:12
            System.getProperty("foo"); //indent:12 exp:12
      } //indent:6 exp:8 warn

        while (test)      {        //     : this is allowed //indent:8 exp:8
              if (test) { //indent:14 exp:12 warn
                  System.getProperty("foo"); //indent:18 exp:16 warn
              } //indent:14 exp:12 warn
              System.getProperty("foo"); //indent:14 exp:12 warn
          } //indent:10 exp:8 warn

        while (test  //indent:8 exp:8
          && 4 < 7 && 8 < 9 //indent:10 exp:12 warn
            && 3 < 4) { //indent:12 exp:>=12
        } //indent:8 exp:8

        while (test  //indent:8 exp:8
            && 4 < 7 && 8 < 9 //indent:12 exp:>=12
          && 3 < 4) { //indent:10 exp:12 warn
        } //indent:8 exp:8

        while (test  //indent:8 exp:8
            && 4 < 7 && 8 < 9 //indent:12 exp:>=12
          && 3 < 4)  //indent:10 exp:12 warn
        { //indent:8 exp:8
        } //indent:8 exp:8

        while (test  //indent:8 exp:8
            && 4 < 7 && 8 < 9 //indent:12 exp:>=12
            && 3 < 4 //indent:12 exp:>=12
     ) { //indent:5 exp:8 warn

        } //indent:8 exp:8

        while (test  //indent:8 exp:8
            && 4 < 7 && 8 < 9 //indent:12 exp:>=12
            && 3 < 4 //indent:12 exp:>=12
          ) { //indent:10 exp:8 warn

        } //indent:8 exp:8

        while (test  //indent:8 exp:8
            && 4 < 7 && 8 < 9 //indent:12 exp:>=12
            && 3 < 4 //indent:12 exp:>=12
          )  //indent:10 exp:8 warn
        { //indent:8 exp:8

        } //indent:8 exp:8

        while (true) //indent:8 exp:8
        { //indent:8 exp:8
        continue; //indent:8 exp:12 warn
        } //indent:8 exp:8
    } //indent:4 exp:4

} //indent:0 exp:0
