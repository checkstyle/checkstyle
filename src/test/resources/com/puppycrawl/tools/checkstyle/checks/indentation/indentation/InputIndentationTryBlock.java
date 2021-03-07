/* Config:                                                                  //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 * basicOffset = 4                                                          //indent:1 exp:1
 * braceAdjustment = 0                                                      //indent:1 exp:1
 * caseIndent = 4                                                           //indent:1 exp:1
 * forceStrictCondition = true                                              //indent:1 exp:1
 * lineWrappingIndentation = 8                                              //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 */                                                                         //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.io.InputStreamReader; //indent:0 exp:0

public class InputIndentationTryBlock { //indent:0 exp:0
    public void delete(final int assetId) { //indent:4 exp:4
        try { //indent:8 exp:8
            new Object().equals(Integer.valueOf(assetId)); //indent:12 exp:12
        } catch (Exception ignore) { //indent:8 exp:8
        } //indent:8 exp:8
        try { //indent:8 exp:8
            boolean a = //indent:12 exp:12
                new Object().equals(Integer.valueOf(assetId)); //indent:16 exp:20 warn
            boolean b = //indent:12 exp:12
            new Object().equals(Integer.valueOf(assetId)); //indent:12 exp:20 warn
        } catch(Exception e) {} //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0
