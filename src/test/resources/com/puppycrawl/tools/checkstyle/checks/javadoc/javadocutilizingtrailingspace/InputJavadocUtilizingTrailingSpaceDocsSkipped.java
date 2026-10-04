/*
JavadocUtilizingTrailingSpace
ignorePattern = (default)href\\s*=\\s*"[^"]*"|http://|https://|ftp://
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for the documentation examples of lines that are not reported.
 */
public class InputJavadocUtilizingTrailingSpaceDocsSkipped {

    /**
     * Refer to the method
     * {@link com.example.registry.status.CompanyStatusRegistry#findStatus(String)}
     * https://example.com/documentation/company-registry/api/v2/status-endpoints/details
     */
    public void longTagAndUrl() { }

    // violation 2 lines below 'Line is longer than 80 characters (found 93)'
    /**
     * {@link com.example.registry.status.CompanyStatusRegistry#findStatus(String)} finds it.
     */
    public void longTagWithWords() { }

    /**
     * <p>Companies House allows you to query
     * information on registered companies in Britain.</p>
     */
    public void htmlTagStartsLine() { }

    // violation 3 lines below 'Line is longer than 80 characters (found 84)'
    /**
     * <ul>
     * <li>Static configuration reads files from the classpath or a given path.</li>
     * </ul>
     */
    public void htmlTextTooLong() { }

    /**
     * <ul>
     * <li>Static configuration reads files from the classpath or a given
     * path.</li>
     * </ul>
     */
    public void htmlTextWrapped() { }

    /**
     * Example:
     * <pre>
     * new CompanyRegistry().findStatus("00000006").orElseThrow(NotRegisteredException::new);
     * </pre>
     */
    public void preContent() { }

    /**
     * See https://example.com/documentation/company-registry/api/v2/status-endpoints/details
     *
     * @see <a href="https://example.com/documentation/company-registry/api/v2/status">
     *     Status endpoints</a>
     */
    public void urlAndLink() { }
}
