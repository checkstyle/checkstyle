package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;      //indent:0 exp:0

/**                                                                          //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:  //indent:1 exp:1
 *                                                                           //indent:1 exp:1
 * arrayInitIndent = 4                                                       //indent:1 exp:1
 * basicOffset = 4                                                           //indent:1 exp:1
 * braceAdjustment = 0                                                       //indent:1 exp:1
 * caseIndent = 4                                                            //indent:1 exp:1
 * forceStrictCondition = false                                              //indent:1 exp:1
 * lineWrappingIndentation = 4                                               //indent:1 exp:1
 * tabWidth = 4                                                              //indent:1 exp:1
 * throwsIndent = 4                                                          //indent:1 exp:1
 *                                                                           //indent:1 exp:1
 *                                                                           //indent:1 exp:1
 */                                                                          //indent:1 exp:1
public class InputIndentationMethodCallLineWrap1 {                           //indent:0 exp:0

    InputIndentationMethodCallLineWrap1[] arr = {this};                      //indent:4 exp:4

    InputIndentationMethodCallLineWrap1[][] arr1 = {this};                   //indent:4 exp:4

    InputIndentationMethodCallLineWrap1 getCurr() {                          //indent:4 exp:4
        return this;                                                         //indent:8 exp:8
    }                                                                        //indent:4 exp:4

    InputIndentationMethodCallLineWrap1[] getCurrArr() {                     //indent:4 exp:4
        return arr;                                                          //indent:8 exp:8
    }                                                                        //indent:4 exp:4

    InputIndentationMethodCallLineWrap1[] getCurrArr1() {                     //indent:4 exp:4
        return arr1;                                                          //indent:8 exp:8
    }                                                                        //indent:4 exp:4

    void method() {                                                          //indent:4 exp:4
        InputIndentationMethodCallLineWrap1 obj =                            //indent:8 exp:8
                new InputIndentationMethodCallLineWrap1();                   //indent:16 exp:16

        obj                                                                  //indent:8 exp:8
            .getCurrArr()[0]                                                 //indent:12 exp:12
            .getCurr()                                                       //indent:12 exp:12
            .getCurr();                                                      //indent:12 exp:12

        obj.getCurrArr()[0]                                                  //indent:8 exp:8
            .getCurr()                                                       //indent:12 exp:12
            .getCurr();                                                      //indent:12 exp:12

        obj.getCurrArr()[0].getCurr()                                        //indent:8 exp:8
            .getCurr();                                                      //indent:12 exp:12

        obj                                                                  //indent:8 exp:8
            .getCurrArr1()[0][0]                                             //indent:12 exp:12
            .getCurr()                                                       //indent:12 exp:12
            .getCurr();                                                      //indent:12 exp:12

        obj.getCurrArr1()[0][0]                                              //indent:8 exp:8
            .getCurr()                                                       //indent:12 exp:12
            .getCurr();                                                      //indent:12 exp:12

        obj.getCurrArr1()[0][0].getCurr()                                    //indent:8 exp:8
            .getCurr();                                                      //indent:12 exp:12

        obj                                                                  //indent:8 exp:8
            .getCurr()                                                       //indent:12 exp:12
            .getCurr()                                                       //indent:12 exp:12
            .getCurr();                                                      //indent:12 exp:12

        obj.getCurr()                                                        //indent:8 exp:8
            .getCurr()                                                       //indent:12 exp:12
            .getCurr();                                                      //indent:12 exp:12

        obj.getCurr().getCurr()                                              //indent:8 exp:8
            .getCurr();                                                      //indent:12 exp:12

    }                                                                        //indent:4 exp:4
}                                                                            //indent:0 exp:0
