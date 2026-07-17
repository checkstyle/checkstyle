package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;  //indent:0 exp:0

/* Config:                                                                 //indent:0 exp:0
 * tabWidth = 4                                                            //indent:1 exp:1
 */                                                                        //indent:1 exp:1

public class InputIndentationCheckMethodParenOnNewLine {                 //indent:0 exp:0
    void                                                                 //indent:4 exp:4
        method (                                                         //indent:8 exp:8
    )                                                                    //indent:4 exp:4
    {                                                                    //indent:4 exp:4
    }                                                                    //indent:4 exp:4
    void                                                                 //indent:4 exp:4
        methodTest (                                                     //indent:8 exp:8
        )                                                                //indent:8 exp:4 warn
    {}                                                                   //indent:4 exp:4
    void                                                                 //indent:4 exp:4
        methodTest2                                                      //indent:8 exp:8
    (                                                                    //indent:4 exp:4
        )                                                                //indent:8 exp:4 warn
    {}                                                                   //indent:4 exp:4
}                                                                        //indent:0 exp:0