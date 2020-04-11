package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

class InputNoWhitespaceBeforeLabelAndSwitch
{
    {
        label1 :  // violation
		switch(1) {
			case 1 : // violation
				break;
			case 2: // no violation
				break;
			default : // violation
				break;
		}
	}

	public void foo() {
		label2: // no violation
		System.out.println();
	}

	public void bar() {
    	int a  = true ? 1 : 0; // no violation
    	for(String s : new String[]{}) {} // no violation
	}
}
