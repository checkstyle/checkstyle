package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;            //indent:0 exp:0

/**                                                                                //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:        //indent:1 exp:1
 *                                                                                 //indent:1 exp:1
 * arrayInitIndent = 4                                                             //indent:1 exp:1
 * basicOffset = 4                                                                 //indent:1 exp:1
 * braceAdjustment = 0                                                             //indent:1 exp:1
 * caseIndent = 4                                                                  //indent:1 exp:1
 * forceStrictCondition = false                                                    //indent:1 exp:1
 * lineWrappingIndentation = 4                                                     //indent:1 exp:1
 * tabWidth = 4                                                                    //indent:1 exp:1
 * throwsIndent = 4                                                                //indent:1 exp:1
 *                                                                                 //indent:1 exp:1
 */                                                                                //indent:1 exp:1
public class Example_ArrayInitIndent {                                             //indent:0 exp:0

    // Example 1: Default arrayInitIndent (4) - correct                            //indent:4 exp:4
    int[] defaultArr = {                                                           //indent:4 exp:4
        1,                                                                         //indent:8 exp:8
        2,                                                                         //indent:8 exp:8
        3                                                                          //indent:8 exp:8
    };                                                                             //indent:4 exp:4

    // Example 2: Incorrect indentation - only 2 spaces instead of 4               //indent:4 exp:4
    int[] wrongArr =                                                               //below indent:4 exp:4
    {
      1,                                                                           //indent:6 exp:8 warn
      2                                                                            //indent:6 exp:8 warn
    };                                                                             //indent:4 exp:4

    // Example 3: Multi-dimensional array - correct                                //indent:4 exp:4
    int[][] matrix = {                                                             //indent:4 exp:4
        {                                                                          //indent:8 exp:8
            1,                                                                     //indent:12 exp:12
            2                                                                      //indent:12 exp:12
        },                                                                         //indent:8 exp:8
        {                                                                          //indent:8 exp:8
            3,                                                                     //indent:12 exp:12
            4                                                                      //indent:12 exp:12
        }                                                                          //indent:8 exp:8
    };                                                                             //indent:4 exp:4

    void method() {                                                                //indent:4 exp:4
        int[] localArr = {                                                         //indent:8 exp:8
            10,                                                                    //indent:12 exp:12
            20,                                                                    //indent:12 exp:12
            30                                                                     //indent:12 exp:12
        };                                                                         //indent:8 exp:8
    }                                                                              //indent:4 exp:4
}                                                                                  //indent:0 exp:0
