package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;     //indent:0 exp:0

/**                                                                         //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 * arrayInitIndent = 4                                                      //indent:1 exp:1
 * basicOffset = 4                                                          //indent:1 exp:1
 * braceAdjustment = 0                                                      //indent:1 exp:1
 * caseIndent = 4                                                           //indent:1 exp:1
 * forceStrictCondition = (default) false                                   //indent:1 exp:1
 * lineWrappingIndentation = 4                                              //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 */                                                                         //indent:1 exp:1
public class InputIndentationInvalidArrayInitIndentWithoutTrailingComments {//indent:0 exp:0

//below indent:4 exp:4
    int[] array1 = new int[]
    //below indent:4 exp:4
    {
    //below indent:4 exp:4
    };
//below indent:4 exp:4
    interface SomeInterface1 {
//below indent:8 exp:8
        @interface SomeAnnotation1 {
//below indent:12 exp:12
            String[] values();
//below indent:12 exp:12
            String[] description() default "";
//below indent:12 exp:12
            String[] description2() default { "hello",
//below indent:12 exp:16,46,48 warn
            "checkstyle"};
//below indent:8 exp:8
        }
//below indent:8 exp:8
        @SomeInterface1.SomeAnnotation1(values =
//below indent:12 exp:12
            {
//below indent:14 exp:12,16 warn
              "hello",
//below indent:12 exp:12
            "checkstyle"
//below indent:12 exp:12
            },
//below indent:12 exp:12
            description = { "hello",
//below indent:14 exp:16,28,30 warn
              "checkstyle"
//below indent:8 exp:12,16 warn
        },
//below indent:12 exp:12
            description2 = {
//below indent:12 exp:16 warn
            "hello", "chekstyle"
//below indent:12 exp:12
            }
//below indent:8 exp:8
        )
//below indent:6 exp:6
      void worked();
//below indent:4 exp:4
    }
//below indent:0 exp:0
}
