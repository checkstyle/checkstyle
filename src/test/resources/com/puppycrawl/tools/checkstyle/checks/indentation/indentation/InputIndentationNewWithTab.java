/* Config:                                                                     //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:    //indent:1 exp:1
 *                                                                             //indent:1 exp:1
 * arrayInitIndent = 4                                                         //indent:1 exp:1
 * basicOffset = 4                                                             //indent:1 exp:1
 * braceAdjustment = 0                                                         //indent:1 exp:1
 * caseIndent = 4                                                              //indent:1 exp:1
 * forceStrictCondition = false                                                //indent:1 exp:1
 * lineWrappingIndentation = 4                                                 //indent:1 exp:1
 * tabWidth = 4                                                                //indent:1 exp:1
 * throwsIndent = 4                                                            //indent:1 exp:1
 *                                                                             //indent:1 exp:1
 */                                                                            //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;        //indent:0 exp:0

public class InputIndentationNewWithTab {                                      //indent:0 exp:0
	void method() {                                                            //indent:4 exp:4
		throw new RuntimeException(                                            //indent:8 exp:8
			"Exception");                                                      //indent:12 exp:12
	}                                                                          //indent:4 exp:4

	private static final class Test18614 {                                     //indent:4 exp:4
		private Test18614 client;                                              //indent:8 exp:8

		private Test18614(String string) {                                     //indent:8 exp:8
		}                                                                      //indent:8 exp:8

		private void test() {                                                  //indent:8 exp:8
			try {                                                              //indent:12 exp:12
				client = new Test18614(                                        //indent:16 exp:16
					null                                                       //indent:20 exp:20
				);                                                             //indent:16 exp:16
			}                                                                  //indent:12 exp:12
			catch (Exception exception) {                                      //indent:12 exp:12
			}                                                                  //indent:12 exp:12
		}                                                                      //indent:8 exp:8
	}                                                                          //indent:4 exp:4
}                                                                              //indent:0 exp:0
