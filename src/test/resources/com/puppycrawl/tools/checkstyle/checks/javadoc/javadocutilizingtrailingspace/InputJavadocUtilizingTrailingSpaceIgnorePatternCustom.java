/*
JavadocUtilizingTrailingSpace
ignorePattern = ^ *\\* *@see .*$
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for a pattern that ignores the lines of a block tag.
 */
public class InputJavadocUtilizingTrailingSpaceIgnorePatternCustom {

    /**
     * @see com.example.registry.status.CompanyStatusRegistry#findStatus(String) the lookup
     */
    public void longSeeIsIgnored() { }

    // violation 2 lines below 'Line is longer than 80 characters (found 83).'
    /**
     * http://example.com/this/is/a/very/long/url/that/exceeds/the/configured/limit
     */
    public void longUrlIsNotIgnored() { }

    // violation 2 lines below 'Line is longer than 80 characters (found 87).'
    /**
     * @return the status of the company, which is defined by the registry of companies
     */
    public String otherBlockTagIsNotIgnored() { return ""; }

    // violation 2 lines below 'Line under-utilized (36/80). Words from below could be moved up'
    /**
     * @see Object the base class of
     *     all classes
     */
    public void shortSeeIsStillTooShort() { }
}
