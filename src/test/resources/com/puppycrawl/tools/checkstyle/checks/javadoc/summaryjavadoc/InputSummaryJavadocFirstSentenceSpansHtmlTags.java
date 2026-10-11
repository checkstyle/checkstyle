/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = (default).


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

/**
 * Input for testing summary whose first sentence starts in one HTML block tag
 * and ends with a period inside another, as rendered by the javadoc tool.
 */
public class InputSummaryJavadocFirstSentenceSpansHtmlTags {

    /**
     * <p>
     * Defines how to configure a new named cache produced by this <code>FactoryBean</code>:
     * <ul>
     * <li>
     * <code>NONE</code>: Configuration starts with a new <code>Configuration</code> instance.
     * Note that this mode may only be used if no named configuration already exists.</li>
     * <li>
     * <code>DEFAULT</code>: Configuration starts with the <code>EmbeddedCacheManager</code>'s
     * <em>default</em> <code>Configuration</code> instance.</li>
     * </ul>
     * </p>
     */
    public void foo1() {}
}
