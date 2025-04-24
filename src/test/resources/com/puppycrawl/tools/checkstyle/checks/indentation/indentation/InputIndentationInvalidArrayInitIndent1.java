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
public class InputIndentationInvalidArrayInitIndent1 { //indent:0 exp:0

    private void func1(int[] arg) {                                      //indent:4 exp:4

    }                                                                    //indent:4 exp:4


    /** Creates a new instance of InputIndentationValidArrayInitIndent *///indent:4 exp:4
    public InputIndentationInvalidArrayInitIndent1() {                   //indent:4 exp:4

        func1(new int[] {                                                //indent:8 exp:8
        1, 2, 3                                                          //indent:8 exp:12 warn
        });                                                              //indent:8 exp:8
    }                                                                    //indent:4 exp:4

    private static char[] sHexChars = {                                  //indent:4 exp:4
        '0', '1', '2', '3', '4', '5', '6', '7',                          //indent:8 exp:8
        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };                        //indent:8 exp:8

    private void myFunc3()                                               //indent:4 exp:4
    { //indent:4 exp:4
        char[] sHexChars2 = {                                         //indent:8 exp:8
          '0', '1', '2', '3', '4', '5', '6', '7',                     //indent:10 exp:12,32,70 warn
              '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };               //indent:14 exp:12,32,70 warn

        char[] sHexChars3 = {                                         //indent:8 exp:8
          '0', '1', '2', '3', '4', '5', '6', '7',                     //indent:10 exp:12,32,70 warn
              '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'                  //indent:14 exp:12,32,70 warn
      };                                                              //indent:6 exp:8,12 warn

        char[] sHexChars4 =                                           //indent:8 exp:8
      {                                                               //indent:6 exp:8,12 warn
              '0', '1', '2', '3', '4', '5', '6', '7',                 //indent:14 exp:10,12,70 warn
          '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'                      //indent:10 exp:10
      };                                                              //indent:6 exp:8,12 warn

    }                                                                 //indent:4 exp:4
    interface Interface1 {                                        //indent:4 exp:4
        @interface Annotation1 {                                  //indent:8 exp:8
            String[] values();                                        //indent:12 exp:12
            String[] description() default "";                        //indent:12 exp:12
            String[] description2() default { "hello",                //indent:12 exp:12
            "checkstyle"};                                            //indent:12 exp:16,46,48 warn
        }                                                             //indent:8 exp:8
        @InputIndentationInvalidArrayInitIndent1.Interface1.Annotation1(values = //indent:8 exp:8
            {                                                            //indent:12 exp:12
              "hello",                                               //indent:14 exp:12,16,73 warn
            "checkstyle"                                             //indent:12 exp:12
            },                                                       //indent:12 exp:12
            description = { "hello",                                 //indent:12 exp:12
              "checkstyle"                                           //indent:14 exp:16,28,30 warn
        },                                                           //indent:8 exp:12,16 warn
            description2 = {                                         //indent:12 exp:12
            "hello", "chekstyle"                                     //indent:12 exp:16,31,69 warn
            }                                                        //indent:12 exp:12
        ) //indent:8 exp:8
      void worked();                                                     //indent:6 exp:6
    }                                                                    //indent:4 exp:4
} //indent:0 exp:0
