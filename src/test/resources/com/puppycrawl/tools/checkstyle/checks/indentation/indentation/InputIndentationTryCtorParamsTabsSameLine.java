/**                                                                          //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:  //indent:1 exp:1
 *                                                                           //indent:1 exp:1
 * basicOffset = 4                                                           //indent:1 exp:1
 * braceAdjustment = 0                                                       //indent:1 exp:1
 * caseIndent = 4                                                            //indent:1 exp:1
 * lineWrappingIndentation = 4                                               //indent:1 exp:1
 * tabWidth = 4                                                              //indent:1 exp:1
 */                                                                          //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;      //indent:0 exp:0

public class InputIndentationTryCtorParamsTabsSameLine {                     //indent:0 exp:0
	private InputIndentationTryCtorParamsTabsSameLine client;                //indent:4 exp:4
	private InputIndentationTryCtorParamsTabsSameLine(String string) {       //indent:4 exp:4
	}                                                                        //indent:4 exp:4
	private void test() {                                                    //indent:4 exp:4
		try {                                                                //indent:8 exp:8
			client = new InputIndentationTryCtorParamsTabsSameLine(null);    //indent:12 exp:12
		}                                                                    //indent:8 exp:8
		catch (Exception e) {                                                //indent:8 exp:8
		}                                                                    //indent:8 exp:8
	}                                                                        //indent:4 exp:4
}                                                                            //indent:0 exp:0
