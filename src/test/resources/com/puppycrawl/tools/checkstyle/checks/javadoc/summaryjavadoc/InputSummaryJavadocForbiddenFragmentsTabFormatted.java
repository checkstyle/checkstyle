/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = ^[a-z]


*/

// Note: This file uses tab indentation. The Javadoc continuation lines
// have tab characters before the asterisks (e.g., "\t *" not " *").
package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocForbiddenFragmentsTabFormatted {
	// violation below, 'Forbidden summary fragment'
	/**
	 * adds an element to the list.
	 *
	 * @param element the element to add
	 */
	public void add(String element) {}

	/**
	 * Adds an element to the list.
	 *
	 * @param element the element to add
	 */
	public void addOk(String element) {}
}
