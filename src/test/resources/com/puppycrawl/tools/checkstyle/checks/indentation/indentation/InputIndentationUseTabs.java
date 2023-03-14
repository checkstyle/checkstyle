package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

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
 * @author  jrichard                                                          //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputIndentationUseTabs { //indent:0 exp:0

	/** Creates a new instance of InputIndentationUseTabs */ //indent:4 exp:4
	public InputIndentationUseTabs() { //indent:4 exp:4
		boolean test = true; //indent:8 exp:8
		if (test) //indent:8 exp:8
		{ //indent:8 exp:8
			while ( //indent:12 exp:12
				test == false) { //indent:16 exp:16
				System.exit(2); //indent:16 exp:16
			} //indent:12 exp:12
		} //indent:8 exp:8
		 System.exit(3); //indent:9 exp:8 warn
	} //indent:4 exp:4

} //indent:0 exp:0
