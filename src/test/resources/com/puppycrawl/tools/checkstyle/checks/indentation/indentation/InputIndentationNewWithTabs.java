package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;        //indent:0 exp:0

/**                                                                            //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:    //indent:1 exp:1
 *                                                                             //indent:1 exp:1
 * basicOffset = 4                                                             //indent:1 exp:1
 * braceAdjustment = 0                                                         //indent:1 exp:1
 * caseIndent = 4                                                              //indent:1 exp:1
 * forceStrictCondition = false                                                //indent:1 exp:1
 * lineWrappingIndentation = 4                                                 //indent:1 exp:1
 * tabWidth = 4                                                                //indent:1 exp:1
 * throwsIndent = 4                                                            //indent:1 exp:1
 *                                                                             //indent:1 exp:1
 */                                                                            //indent:1 exp:1
public class InputIndentationNewWithTabs {                                     //indent:0 exp:0

	static class Inner {                                                       //indent:4 exp:4
		Inner(String s) {                                                      //indent:8 exp:8
		}                                                                      //indent:8 exp:8
	}                                                                          //indent:4 exp:4

	private Inner client;                                                      //indent:4 exp:4

	public void testCorrectNewInTryCatch() {                                   //indent:4 exp:4
		try {                                                                  //indent:8 exp:8
			client = new Inner(                                                //indent:12 exp:12
				null                                                           //indent:16 exp:16
			);                                                                 //indent:12 exp:12
		} catch (Exception e) {                                                //indent:8 exp:8
		}                                                                      //indent:8 exp:8
	}                                                                          //indent:4 exp:4

	public void testCorrectNewLineWrapped() {                                  //indent:4 exp:4
		client =                                                               //indent:8 exp:8
			new Inner(                                                         //indent:12 exp:12
				null                                                           //indent:16 exp:16
			);                                                                 //indent:12 exp:12
	}                                                                          //indent:4 exp:4

	public void testWrongNewIndent() {                                         //indent:4 exp:4
		try {                                                                  //indent:8 exp:8
			client =                                                           //indent:12 exp:12
	new Inner(null);                                                           //indent:4 exp:16 warn
		} catch (Exception e) {                                                //indent:8 exp:8
		}                                                                      //indent:8 exp:8
	}                                                                          //indent:4 exp:4
}                                                                              //indent:0 exp:0
