/* Config:                                                                    //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = true                                                //indent:1 exp:1
 * lineWrappingIndentation = 8                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;       //indent:0 exp:0
import java.io.BufferedReader; //indent:0 exp:0
import java.io.IOException; //indent:0 exp:0
import java.io.InputStreamReader; //indent:0 exp:0

public class InputIndentationNewWithForceStrictCondition { //indent:0 exp:0
    void test() throws IOException  { //indent:4 exp:4
        BufferedReader bf =  //indent:8 exp:8
                new BufferedReader(  //indent:16 exp:16
                new InputStreamReader(System.in) {  //indent:16 exp:24 warn
                    int a = 0; //indent:20 exp:28,32,36 warn
                }); //indent:16 exp:24,28,32 warn
    } //indent:4 exp:4
}  //indent:0 exp:0
