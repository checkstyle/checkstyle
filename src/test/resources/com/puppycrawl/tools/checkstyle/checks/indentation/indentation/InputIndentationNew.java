/* Config:                                                                    //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 8                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.io.BufferedReader; //indent:0 exp:0
import java.io.IOException; //indent:0 exp:0
import java.io.InputStreamReader; //indent:0 exp:0
import java.lang.Integer; //indent:0 exp:0

public class InputIndentationNew { //indent:0 exp:0
    void test() throws IOException  { //indent:4 exp:4
        BufferedReader bf =  //indent:8 exp:8
                new BufferedReader(  //indent:16 exp:16
                        new InputStreamReader(System.in) {  //indent:24 exp:24
                            int a = 0; //indent:28 exp:28
                        }); //indent:24 exp:24
    } //indent:4 exp:4
    public void foo() { //indent:4 exp:4
        boolean flag = true; //indent:8 exp:8
        Integer result = flag ? //indent:8 exp:8
                new Integer(1) : //indent:16 exp:16
                new Integer(2); //indent:16 exp:16
    } //indent:4 exp:4

    public Integer bar() { //indent:4 exp:4
        boolean flag = true; //indent:8 exp:8
        return flag ? //indent:8 exp:8
                new Integer(1) : new Integer(2); //indent:16 exp:16
    } //indent:4 exp:4
}  //indent:0 exp:0
