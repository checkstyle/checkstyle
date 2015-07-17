package com.puppycrawl.tools.checkstyle.indentation; //indent:0 exp:0


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
public class InputMethodCallLineWrap { //indent:0 exp:0

    void foo() { //indent:4 exp:4
        new String() //indent:8 exp:8
            .substring( //indent:12 exp:12
                0, 100 //indent:16 exp:16
            ) //indent:12 exp:12
            .substring( //indent:12 exp:12
                0, 50 //indent:16 exp:16
            ); //indent:12 exp:12
    } //indent:4 exp:4

    class InnerFoo { //indent:4 exp:4

    	void foo() { //indent:8 exp:8
            new String() //indent:12 exp:12
                .substring( //indent:16 exp:16
                    0, 100 //indent:20 exp:20
                ) //indent:16 exp:16
                .substring( //indent:16 exp:16
                    0, 50 //indent:20 exp:20
                ); //indent:16 exp:16
        } //indent:8 exp:8
    } //indent:4 exp:4

    InnerFoo anon = new InnerFoo() { //indent:4 exp:4

    	void foo() { //indent:8 exp:8
            new String() //indent:12 exp:12
                .substring( //indent:16 exp:16
                    0, 100 //indent:20 exp:20
                ) //indent:16 exp:16
                .substring( //indent:16 exp:16
                  0, 50 //indent:18 exp:20 warn
              ); //indent:14 exp:16 warn
        } //indent:8 exp:8
    }; //indent:4 exp:4

    void chaining() { //indent:4 exp:4
        toString() //indent:8 exp:8
                .getClass(); //indent:16 exp:16
        toString().contains(//indent:8 exp:8
            new String(//indent:12 exp:12
                    "a" //indent:20 exp:20
            )//indent:12 exp:12
        ); //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0
