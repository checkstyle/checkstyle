/* Config:                                                                    //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * forceStrictCondition = true                                                //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationNewWithTernaryOp2 { //indent:0 exp:0
    Integer foo2(boolean flag) { //indent:4 exp:4
        Integer result = flag ? //indent:8 exp:8
            new Integer(1) : //indent:12 exp:12
            new Integer(2); //indent:12 exp:12

        return result; //indent:8 exp:8
    } //indent:4 exp:4

    Integer bar2(boolean flag) { //indent:4 exp:4
        return flag ? //indent:8 exp:8
            new Integer(1) : new Integer(2); //indent:12 exp:12
    } //indent:4 exp:4
} //indent:0 exp:0
