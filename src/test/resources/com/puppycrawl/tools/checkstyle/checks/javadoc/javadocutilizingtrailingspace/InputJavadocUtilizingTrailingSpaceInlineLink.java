/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for {@link Object} inline tag handling.
 */
public class InputJavadocUtilizingTrailingSpaceInlineLink {

    // violation 2 lines below 'Line is longer than 80 characters (found 116).'
    /**
     * {@link com.very.wide.bundles.name.that.exceeds.limit.SomeClass} testing and make sure we're on the safe side.
     */
    public void longLinkAtStartWithTextViolation() { }

    /**
     * {@link com.very.wide.bundles.name.that.exceeds.limit.SomeClass} testing
     * and make sure we're on the safe side.
     */
    public void correctedLongLinkAtStartWithTextViolation() { }

    /**
     * {@link com.very.wide.bundles.name.that.exceeds.limit.SomeClass#someLongMethodName()}.
     */
    public void longLinkAtStartAllowed() { }

    /** {@link com.very.wide.bundles.name.that.exceeds.limit.SomeClass#someLongMethodName()}. */
    public void longLinkInSingleLineJavadocAllowed() { }

    /**
     * Refer to the method
     * {@link com.very.wide.bundles.name.that.exceeds.limit.SomeClass#someLongMethodName()}
     * {@code com.very.wide.bundles.name.that.exceeds.limit.SomeClass.someLongMethodName()}
     */
    public void consecutiveLongTagsAllowed() { }

    // violation 2 lines below 'Line is longer than 80 characters (found 96).'
    /**
     * {@link com.very.wide.bundles.name.that.exceeds.limit.SomeClass} {@link Object#toString()}
     */
    public void twoTagsAtStartViolation() { }

    // violation 2 lines below 'Line is longer than 80 characters (found 92).'
    /**
     * See {@link com.very.wide.bundles.name.that.exceeds.limit.SomeClass} for more details.
     */
    public void longLinkInMiddleViolation() { }

    /**
     * See
     * {@link com.very.wide.bundles.name.that.exceeds.limit.SomeClass} for more
     * details.
     */
    public void correctedLongLinkInMiddleViolation() { }

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
     * {@link com.very.wide.bundles.name.that.exceeds.limit.SomeClass}
     */
    public void fixedLongLink() { }

    /** {@link Object} simple. */
    public void singleLineWithLink() { }

    /**
     * {@link com.example.SomeClass#someMethod(String, int)}
     */
    public void seeWithLink() { }

    /**
     * Uses {@link java.util.List} and {@link java.util.Map} collections.
     */
    public void commonLinks() { }

    private void someMethod() { }
}
