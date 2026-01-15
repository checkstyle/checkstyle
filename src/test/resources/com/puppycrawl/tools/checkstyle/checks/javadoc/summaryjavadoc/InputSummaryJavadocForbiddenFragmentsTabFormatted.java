/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = ^[a-z]


*/


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
