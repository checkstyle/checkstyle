/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for {@link} inline tag handling.
 */
public class InputJavadocUtilizingTrailingSpaceInlineLink {

    /**
     * {@link com.very.long.package.name.that.exceeds.limit.SomeClass}
     */
    public void longLinkAtStartAllowed() { }

    // violation 2 below 'Line is longer than'
    /**
     * See {@link com.very.long.package.name.that.exceeds.limit.SomeClass} for more details.
     */
    public void longLinkInMiddleViolation() { }

    /**
     * This method works with {@link String} objects.
     */
    public void shortLinkInMiddle() { }

    /**
     * Reference to {@link #someMethod()} within the same class.
     */
    public void internalLink() { }

    /**
     * Multiple links: {@link String} and {@link Object} are used here.
     */
    public void multipleShortLinks() { }

    /**
     * See the documentation of
     * {@link com.very.long.package.name.that.exceeds.limit.SomeClass}
     */
    public void fixedLongLink() { }

    /** {@link Object} simple. */
    public void singleLineWithLink() { }

    /**
     * @see {@link com.example.SomeClass#someMethod(String, int)}
     */
    public void seeWithLink() { }

    /**
     * Uses {@link java.util.List} and {@link java.util.Map} collections.
     */
    public void commonLinks() { }

    private void someMethod() { }
}
