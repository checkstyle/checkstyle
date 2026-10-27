/*
OpenjdkMethodParameterAlignment
tabWidth = 8


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.openjdkmethodparameteralignment;

public class InputOpenjdkMethodParameterAlignmentTabs {

	void aligned(int a,
		     int b,
		     int c) {
	}

	void wrappedByEightSpaces(int a, int b,
		int c) {
	}

	void misaligned(int a,
		   int b,
		   int c) {
		// violation 3 lines above 'Parameter should be aligned vertically or indented 8 spaces'
	}

	void twoOnOneLine(int a,
			  int b, int c) {
		// violation 2 lines above 'Only one parameter is allowed per line in a vertical list.'
	}
}
