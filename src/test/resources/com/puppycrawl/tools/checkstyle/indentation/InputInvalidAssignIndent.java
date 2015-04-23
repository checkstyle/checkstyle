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
public class InputInvalidAssignIndent //indent:0 exp:0
{ //indent:0 exp:0
    void foo(String[] args) //indent:4 exp:4
    { //indent:4 exp:4
        String line = mIndentCheck[ //indent:8 exp:8
          getLineNo()]; //indent:10 exp:12 warn
        String line1 = //indent:8 exp:8
          getLine(); //indent:10 exp:12 warn
        line1 = //indent:8 exp:8
          getLine(); //indent:10 exp:10
        int i //indent:8 exp:8
         = //indent:9 exp:12 warn
          1; //indent:10 exp:12 warn
        //     : this should be illegal. //indent:8 exp:8
        i = //indent:8 exp:8
            3; //indent:12 exp:12
        //     : add more testing //indent:8 exp:8
    } //indent:4 exp:4

    private String[] mIndentCheck = null; //indent:4 exp:4
    int getLineNo() { //indent:4 exp:4
        return 1; //indent:8 exp:8
    } //indent:4 exp:4
    String getLine() { //indent:4 exp:4
        return ""; //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0
