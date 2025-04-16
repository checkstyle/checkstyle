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

    private void func1(int[] arg) {

    }


    /** Creates a new instance of InputIndentationValidArrayInitIndent */
    public InputIndentationInvalidArrayInitIndent1() {

        func1(new int[] {
        1, 2, 3
        });
    }

    private static char[] sHexChars = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private void myFunc3()
    { //indent:4 exp:4
        char[] sHexChars2 = {
          '0', '1', '2', '3', '4', '5', '6', '7',
              '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

        char[] sHexChars3 = {
          '0', '1', '2', '3', '4', '5', '6', '7',
              '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
      };

        char[] sHexChars4 =
      {
              '0', '1', '2', '3', '4', '5', '6', '7',
          '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
      };

    }
    interface SomeInterface1 {
        @interface SomeAnnotation1 {
            String[] values();
            String[] description() default "";
            String[] description2() default { "hello",
            "checkstyle"};
        }
        @InputIndentationInvalidArrayInitIndent1.SomeInterface1.SomeAnnotation1(values =
            {
              "hello",
            "checkstyle"
            },
            description = { "hello",
              "checkstyle"
        },
            description2 = {
            "hello", "chekstyle"
            }
        ) //indent:8 exp:8
      void worked();
    }
} //indent:0 exp:0
