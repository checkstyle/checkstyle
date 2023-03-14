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
public class InputIndentationInvalidForIndent { //indent:0 exp:0

    /** Creates a new instance of InputIndentationValidForIndent */ //indent:4 exp:4
    public InputIndentationInvalidForIndent() { //indent:4 exp:4
    } //indent:4 exp:4


    private void method1() //indent:4 exp:4
    { //indent:4 exp:4
      for (int i=0; i<10; i++) { //indent:6 exp:8 warn
          } //indent:10 exp:8 warn

         for (int i=0; i<10; i++)  //indent:9 exp:8 warn
      { //indent:6 exp:8 warn
      } //indent:6 exp:8 warn

        for (int i=0; i<10; i++)  //indent:8 exp:8
        { //indent:8 exp:8
          System.getProperty("foo"); //indent:10 exp:12 warn
          } //indent:10 exp:8 warn

        for (int i=0; i<10; i++)  //indent:8 exp:8
          { //indent:10 exp:8 warn
          boolean test = true; //indent:10 exp:12 warn
            if (test) { // mixed styles are OK //indent:12 exp:12
                System.getProperty("foo"); //indent:16 exp:16
            } //indent:12 exp:12
        } //indent:8 exp:8

        for ( //indent:8 exp:8
            int i=0;  //indent:12 exp:>=12
          i<10;  //indent:10 exp:>=12 warn
            i++)  //indent:12 exp:>=12
        { //indent:8 exp:8

        } //indent:8 exp:8

       for ( //indent:7 exp:8 warn
          int i=0;  //indent:10 exp:>=12 warn
            i<10;  //indent:12 exp:>=12
            i++)  //indent:12 exp:>=12
        { //indent:8 exp:8

        } //indent:8 exp:8

        for (int i=0;  //indent:8 exp:8
            i<10;  //indent:12 exp:>=12
       i++)  //indent:7 exp:>=12 warn
        { //indent:8 exp:8

        } //indent:8 exp:8

      for (int i=0;  //indent:6 exp:8 warn
          i<10 && 4<5 //indent:10 exp:>=12 warn
              && 7<8;  //indent:14 exp:>=16 warn
          i++)  //indent:10 exp:12 warn
        { //indent:8 exp:8
        } //indent:8 exp:8

        for (int i=0; i<10; i++) { //indent:8 exp:8
            System.getProperty("foo"); } //indent:12 exp:12

        for (int i=0;  //indent:8 exp:8
            i<10; i++ //indent:12 exp:>=12
            ) { //indent:12 exp:8 warn
            System.getProperty("foo"); //indent:12 exp:12
        } //indent:8 exp:8
    } //indent:4 exp:4

  public void doSmth() { //indent:2 exp:4 warn
    for (int h //indent:4 exp:8 warn
        : new int[] {}) { //indent:8 exp:12 warn
      System.getProperty( //indent:6 exp:12 warn
        "someString"); //indent:8 exp:16 warn
        } //indent:8 exp:8
for //indent:0 exp:8 warn
( //indent:0 exp:8 warn
int i = 0 //indent:0 exp:12 warn
; //indent:0 exp:4 warn
i < 5 //indent:0 exp:12 warn
; //indent:0 exp:4 warn
i++) {} //indent:0 exp:12 warn
    } //indent:4 exp:4

} //indent:0 exp:0
