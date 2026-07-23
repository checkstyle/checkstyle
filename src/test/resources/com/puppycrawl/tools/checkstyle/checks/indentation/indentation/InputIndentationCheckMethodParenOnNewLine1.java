package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;  //indent:0 exp:0

/* Config:                                                                 //indent:0 exp:0
 * basicOffset = 4                                                         //indent:1 exp:1
 * braceAdjustment = 0                                                     //indent:1 exp:1
 * caseIndent = 4                                                          //indent:1 exp:1
 * throwsIndent = 4                                                        //indent:1 exp:1
 * arrayInitIndent = 4                                                     //indent:1 exp:1
 * lineWrappingIndentation = 4                                             //indent:1 exp:1
 * tabWidth = 4                                                            //indent:1 exp:1
 * forceStrictCondition = false                                            //indent:1 exp:1
 */                                                                        //indent:1 exp:1

public class InputIndentationCheckMethodParenOnNewLine1 {                  //indent:0 exp:0
    void                                                                   //indent:4 exp:4
        method (                                                           //indent:8 exp:8
    )                                                                      //indent:4 exp:4
    {                                                                      //indent:4 exp:4
        int b =                                                            //indent:8 exp:8
         2                                                                 //indent:9 exp:12 warn
            *                                                              //indent:12 exp:12
            3;                                                             //indent:12 exp:12
    }                                                                      //indent:4 exp:4
    void                                                                   //indent:4 exp:4
        methodTest (                                                       //indent:8 exp:8
       int a                                                               //indent:7 exp:8 warn
        )                                                                  //indent:8 exp:4 warn
    {}                                                                     //indent:4 exp:4
}                                                                          //indent:0 exp:0
