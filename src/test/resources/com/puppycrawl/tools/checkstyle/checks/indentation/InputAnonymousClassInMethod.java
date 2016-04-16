package com.puppycrawl.tools.checkstyle.checks.indentation; //indent:0 exp:0

import java.io.File; //indent:0 exp:0
import java.io.FileFilter; //indent:0 exp:0

/**                                                                         //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 * arrayInitIndent = 2                                                      //indent:1 exp:1
 * basicOffset = 2                                                          //indent:1 exp:1
 * braceAdjustment = 0                                                      //indent:1 exp:1
 * caseIndent = 2                                                           //indent:1 exp:1
 * forceStrictCondition = false                                             //indent:1 exp:1
 * lineWrappingIndentation = 4                                              //indent:1 exp:1
 * tabWidth = 8                                                             //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 */                                                                         //indent:1 exp:1
public class InputAnonymousClassInMethod { //indent:0 exp:0
	private void walkDir(File dir, FileFilter fileFilter) { //indent:8 exp:2 warn
		walkDir( dir, new FileFilter() { //indent:16 exp:4 warn
			@Override //indent:24 exp:18,20,22 warn
			public boolean accept(File path) { //indent:24 exp:24
				return ( path.isDirectory() ); //indent:32 exp:20,22,24 warn
			} //indent:24 exp:18,20,22 warn
		} ); //indent:16 exp:16
	} //indent:8 exp:2 warn
} //indent:0 exp:0
