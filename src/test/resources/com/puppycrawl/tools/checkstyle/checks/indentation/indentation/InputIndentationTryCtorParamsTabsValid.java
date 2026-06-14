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

public class InputIndentationTryCtorParamsTabsValid {                        //indent:0 exp:0
	private InputIndentationTryCtorParamsTabsValid client;                   //indent:4 exp:4
	private InputIndentationTryCtorParamsTabsValid(String string) {          //indent:4 exp:4
	}                                                                        //indent:4 exp:4
	private void test() {                                                    //indent:4 exp:4
		try {                                                                //indent:8 exp:8
			client =                                                         //indent:12 exp:12
				new InputIndentationTryCtorParamsTabsValid(null);            //indent:16 exp:16
		}                                                                    //indent:8 exp:8
		catch (Exception e) {                                                //indent:8 exp:8
		}                                                                    //indent:8 exp:8
	}                                                                        //indent:4 exp:4
}                                                                            //indent:0 exp:0
