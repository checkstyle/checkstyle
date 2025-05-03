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

    int[] array1 = new int[]                          //indent:4 exp:4
//below indent:4 exp:4
    {

    };                                                //indent:4 exp:4
    interface SomeInterface1 {                        //indent:4 exp:4
        @interface SomeAnnotation1 {                  //indent:8 exp:8
            String[] values();                        //indent:12 exp:12
            String[] description() default "";        //indent:12 exp:12
            String[] description2() default { "hello",//indent:12 exp:12
            "checkstyle"};                            //indent:12 exp:16,46,48 warn
        }                                             //indent:8 exp:8
        @SomeInterface1.SomeAnnotation1(values =      //indent:8 exp:8
//below indent:12 exp:12
            {
//below indent:14 exp:12,16 warn
              "hello",
            "checkstyle"                              //indent:12 exp:12
            },                                        //indent:12 exp:12
            description = { "hello",                  //indent:12 exp:12
              "checkstyle"                            //indent:14 exp:16,28,30 warn
        },                                            //indent:8 exp:12,16 warn
//below indent:12 exp:12
            description2 = {
            "hello", "chekstyle"                      //indent:12 exp:16 warn
            }                                         //indent:12 exp:12
        )                                             //indent:8 exp:8
      void worked();                                  //indent:6 exp:6
    }                                                 //indent:4 exp:4
}                                                     //indent:0 exp:0
