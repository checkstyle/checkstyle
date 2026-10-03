/*
OpenjdkMethodParameterAlignment
tabWidth = 4


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.openjdkmethodparameteralignment;

public class InputOpenjdkMethodParameterAlignmentTabsWidthFour {

	void aligned(int a,
	             int b) {
	}

	void mixedIndent(int a,
				     int b) {
	}

	void wrappedByEightSpaces(int a, int b,
			int c) {
	}

	void tabsBeforeComma(int a
	                     , int b) {
	}

	void misaligned(int a,
		int b) {
		// violation 2 lines above 'Parameter should be aligned vertically or indented 8 spaces'
	}

	void twoOnOneLine(int a,
	                  int b, int c) {
		// violation 2 lines above 'Only one parameter is allowed per line in a vertical list.'
	}
}
