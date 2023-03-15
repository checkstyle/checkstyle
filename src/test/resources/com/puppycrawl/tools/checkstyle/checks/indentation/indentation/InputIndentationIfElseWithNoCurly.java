/* Config:                                                                    //indent:0 exp:0
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
 */                                                                           //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationIfElseWithNoCurly { //indent:0 exp:0
    void test() { //indent:4 exp:4
        if (true); //indent:8 exp:8
        if (true) //indent:8 exp:8
expression(); //indent:0 exp:12 warn
        if (true) //indent:8 exp:8
            expression(); //indent:12 exp:12
        if (true) //indent:8 exp:8
                expression(); //indent:16 exp:16
    if (true) //indent:4 exp:8 warn
        expression(); //indent:8 exp:12 warn
        if(true) //indent:8 exp:8
            expression(); //indent:12 exp:12
        else expression(); //indent:8 exp:8
        if (true) expression(); //indent:8 exp:8
        else //indent:8 exp:8
            expression(); //indent:12 exp:12
        if (true) expression(); //indent:8 exp:8
        else //indent:8 exp:8
                expression(); //indent:16 exp:16
        if (true); //indent:8 exp:8
            else expression(); //indent:12 exp:8 warn
        if(true) //indent:8 exp:8
        if(true); //indent:8 exp:12 warn
        boolean a = false; //indent:8 exp:8
        if (true) //indent:8 exp:8
            a = true //indent:12 exp:12
        && false; //indent:8 exp:16 warn
        if (true) //indent:8 exp:8
            a = true //indent:12 exp:12
                    && false; //indent:20 exp:20
        if (true) //indent:8 exp:8
            a = true //indent:12 exp:12
                && false; //indent:16 exp:16
    } //indent:4 exp:4

    void expression() {} //indent:4 exp:4
} //indent:0 exp:0
