/*
OpenjdkMethodParameterAlignment
tokens = (default)METHOD_DEF, CTOR_DEF


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
		// violation 3 lines above 'Align parameters vertically or wrap with eight extra spaces.'
	}

	void twoOnOneLine(int a,
			  int b, int c) {
		// violation 2 lines above 'Only one parameter is allowed per line in a vertical list.'
	}
}
