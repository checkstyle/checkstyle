/*
OpenjdkLineWrapping
tabWidth = 4

*/

package com.puppycrawl.tools.checkstyle.checks.indentation.openjdklinewrapping;

class InputOpenjdkLineWrappingTabs {
	void method() {
		int value = 1
				+ 2;
		int wrong = 1
			+ 2; // violation 'incorrect indentation level 12'
	}
}
