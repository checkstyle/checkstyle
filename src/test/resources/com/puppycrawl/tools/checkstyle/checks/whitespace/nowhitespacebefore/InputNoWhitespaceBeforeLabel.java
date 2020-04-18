package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

class InputNoWhitespaceBeforeLabel
{
    {
        label1 :  // violation
		System.out.println();
	}

	public void foo() {
		label2: // no violation
		System.out.println();
	}
}
