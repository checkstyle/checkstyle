package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * forceStrictCondition = true                                                //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1

public class InputIndentationMethodCallLineWrap2 {                            //indent:0 exp:0

    InputIndentationMethodCallLineWrap2[] arr = {this};                       //indent:4 exp:4
    InputIndentationMethodCallLineWrap2[][] arr2 = {{this}, {this}};          //indent:4 exp:4

    InputIndentationMethodCallLineWrap2 obj() {                               //indent:4 exp:4
        return this;                                                          //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    InputIndentationMethodCallLineWrap2[] obj2() {                            //indent:4 exp:4
        return arr;                                                           //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    InputIndentationMethodCallLineWrap2[][] obj3() {                          //indent:4 exp:4
        return arr2;                                                          //indent:8 exp:8
    }                                                                         //indent:4 exp:4

    void testMethod() {                                                       //indent:4 exp:4
        InputIndentationMethodCallLineWrap2 obj =                             //indent:8 exp:8
            new InputIndentationMethodCallLineWrap2();                        //indent:12 exp:12

        obj                                                                   //indent:8 exp:8
            .obj2()[0]                                                        //indent:12 exp:12
            .obj()                                                            //indent:12 exp:12
            .obj();                                                           //indent:12 exp:12

       obj                                                                    //indent:7 exp:8 warn
            .obj2()[0]                                                        //indent:12 exp:12
            .obj()                                                            //indent:12 exp:12
            .obj()                                                            //indent:12 exp:12
            .obj(                                                             //indent:12 exp:12
            )                                                                 //indent:12 exp:12
            .obj2()[0]                                                        //indent:12 exp:12
            .obj()                                                            //indent:12 exp:12
            .obj();                                                           //indent:12 exp:12

        obj.obj()                                                             //indent:8 exp:8
            .obj3()[0][0]                                                     //indent:12 exp:12
            .obj()                                                            //indent:12 exp:12
            .obj2()[                                                          //indent:12 exp:12
                    0]                                                        //indent:20 exp:20
            .obj()                                                            //indent:12 exp:12
            .obj();                                                           //indent:12 exp:12

    }                                                                         //indent:4 exp:4

}                                                                             //indent:0 exp:0
