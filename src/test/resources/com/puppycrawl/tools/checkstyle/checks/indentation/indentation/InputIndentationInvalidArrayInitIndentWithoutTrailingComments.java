package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition =                                                     //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputIndentationInvalidArrayInitIndentWithoutTrailingComments {

    int[] array1 = new int[]
    {

    };
    interface SomeInterface1 {
        @interface SomeAnnotation1 {
            String[] values();
            String[] description() default "";
            String[] description2() default { "hello",
            "checkstyle"};
        }
        @SomeInterface1.SomeAnnotation1(values =
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
}
