package com.puppycrawl.tools.checkstyle.checks.indentation.leadingasteriskalign;

/* Config:
 * option = "RIGHT"
 * tabSize = "4"
 */
public class InputLeadingAsteriskAlignTabsAndSpaces {

	/**
	 * This is the javadoc for the constructor.
	 * It uses both tabs and spaces for indentation.
	 * Tab
     * Spaces
	 * Tab
     */
// ^spaces
	public InputLeadingAsteriskAlignTabsAndSpaces() {}
}
