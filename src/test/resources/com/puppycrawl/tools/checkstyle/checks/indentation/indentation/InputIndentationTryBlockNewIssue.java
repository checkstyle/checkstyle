package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationTryBlockNewIssue { 					    //indent:0 exp:0
	static class Test {                                                 //indent:4 exp:4
		Test(String s) {}                                               //indent:8 exp:8
	}                                                                   //indent:4 exp:4

	public void testMethod() {                                          //indent:4 exp:4
		Test client = null;                                             //indent:8 exp:8
		try {                                                           //indent:8 exp:8
			client = new Test(                                          //indent:12 exp:12
				null                                                    //indent:16 exp:16
			);                                                          //indent:12 exp:12
		} catch (Exception e) {                                         //indent:8 exp:8
		}                                                               //indent:8 exp:8
	}                                                                   //indent:4 exp:4
}                                                                       //indent:0 exp:0
