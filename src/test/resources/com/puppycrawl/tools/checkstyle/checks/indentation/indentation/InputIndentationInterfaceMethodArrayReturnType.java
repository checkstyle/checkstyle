package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;     //indent:0 exp:0

/**                                                                         //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 * arrayInitIndent = 4                                                      //indent:1 exp:1
 * basicOffset = 4                                                          //indent:1 exp:1
 * braceAdjustment = 0                                                      //indent:1 exp:1
 * caseIndent = 4                                                           //indent:1 exp:1
 * forceStrictCondition = false                                             //indent:1 exp:1
 * lineWrappingIndentation = 4                                              //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 */                                                                         //indent:1 exp:1
interface InputIndentationInterfaceMethodArrayReturnType {                  //indent:0 exp:0
void                                                                        //indent:0 exp:4 warn
method1();                                                                  //indent:0 exp:4 warn
String                                                                      //indent:0 exp:4 warn
method2();                                                                  //indent:0 exp:4 warn
String[]                                                                    //indent:0 exp:4 warn
method3();                                                                  //indent:0 exp:4 warn

    String[]                                                                //indent:4 exp:4
        method4();                                                          //indent:8 exp:8
    String[] method5();                                                     //indent:4 exp:4
}                                                                           //indent:0 exp:0
