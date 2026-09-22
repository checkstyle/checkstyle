/*
JavadocUtilizingTrailingSpace
ignorePattern = (default)href\\s*=\\s*"[^"]*"|http://|https://|ftp://
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for lines that start with an HTML tag.
 */
public class InputJavadocUtilizingTrailingSpaceHtmlLineStart {

    // violation 9 lines below 'Line is longer than 80 characters (found 143).'
    // violation 9 lines below 'Line is longer than 80 characters (found 141).'
    /**
     * Bootstraps and initializes the application, and pulls in your
     * configuration and runtime dependencies.
     *
     * <p>There are two ways to configure the application:</p>
     *
     * <ul>
     * <li><strong>Static Configuration:</strong> Uses configuration files either in the classpath or externally, and {@link Annotation}s.</li>
     * <li><strong>Dependency Injection Modules:</strong> This entails overriding runtime dependencies for the CDI/IoC container to use.</li>
     * </ul>
     */
    public void longListItems() { }

    /**
     * Bootstraps and initializes the application, and pulls in your
     * configuration and runtime dependencies.
     *
     * <p>There are two ways to configure the application:</p>
     *
     * <ul>
     * <li><strong>Static Configuration:</strong> Uses configuration files
     * either in the classpath or externally, and {@link Annotation}s.</li>
     * <li><strong>Dependency Injection Modules:</strong> This entails
     * overriding runtime dependencies for the CDI/IoC container to use.</li>
     * </ul>
     */
    public void wrappedListItems() { }

    /**
     * Company number stored by Companies House.
     *
     * <p>Is it stored as a string as the company number can begin with letters
     * or padded zeros.</p>
     *
     * @return Company number of the registered company.
     */
    public String paragraphStartsLine() { return ""; }

    /**
     * Company number stored by Companies House.
     *
     * <p>
     * Is it stored as a string as the company number can begin with letters or
     * padded zeros.
     * </p>
     *
     * @return Company number of the registered company.
     */
    public String paragraphOnOwnLines() { return ""; }

    // violation 2 lines below 'Line is longer than 80 characters (found 87).'
    /**
     * Some text that leads up to a bold word at the end of a line and <b>overflows</b>
     */
    public void inlineTagAtEnd() { }

    /**
     * Some text that leads up to a bold word at the end of a line and
     * <b>overflows</b>
     */
    public void correctedInlineTagAtEnd() { }
}
