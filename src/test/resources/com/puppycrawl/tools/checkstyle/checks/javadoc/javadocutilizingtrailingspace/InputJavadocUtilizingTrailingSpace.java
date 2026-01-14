/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

public class InputJavadocUtilizingTrailingSpace {
    /**
     * <p>
     * This paragraph starts with an HTML tag and is ignored.
     * </p>
     */
    public void htmlStructuralIgnored() { }

    // violation 3 lines below 'Line is smaller than 80 characters (found 14).'
    // violation 3 lines below 'Line is smaller than 80 characters (found 39).'
    /**
     * @return
     * This line is intentionally short
     * and should be reported as too short.
     */
    public int tooShortWithFollowingContent() {
        return 0;
    }

    /**
     * {@link com.very.long.package.name.that.exceeds.limit.CompanyStatus}
     */
    public void longInlineTagAtStartAllowed() { }

    // violation 2 lines below 'Line is smaller than 80 characters (found 59).'
    /**
     * inline {@link long.package.name.limit.CompanyStatus}
     * reference in the middle and should be too long. 
     */
    public void longInlineTagInMiddleViolation() { }

    /**
     * This line is wrapped correctly 
     * {@link com.very.long.package.name.that.exceeds.limit.CompanyStatus}
     */
    public void fixedLongInlineTag() { }

    /**
     * <pre>
     * This is inside a pre block and should be ignored even if the line is very very very long.
     * </pre>
     */
    public void preBlockIgnored() { }

    // violation 3 lines below 'Line is smaller than 80 characters (found 20).'
    // violation 3 lines below 'Line is smaller than 80 characters (found 44).'
    /**
     * @param valued
     * a parameter description that is short
     * but followed by another content line.
     */
    public void blockTagValueTooShort(int value) { }

    /**
     * http://example.com/this/is/a/very/long/url/that/exceeds/the/configured/limit
     */
    public void longUrlAtStartAllowed() { }

    // violation 2 lines below 'Line is longer than 80 characters (found 108).'
    /**
     * See the documentation at http://example.com/this/is/a/very/long/url/that/exceeds/the/configured/limit
     * for more details.
     */
    public void longUrlInMiddleViolation() { }
}
