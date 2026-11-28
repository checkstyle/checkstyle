package com.openjdk.checkstyle.test.chapterformatting.rulejavadoc;

// violation first line 'Header mismatch'

public class InputJavadocDosAndDonts {

    /** A short javadoc comment. */
    public void styleGuideDo1() {
    }

    /**
     * Short summary.
     *
     * <blockquote>{@code
     *     List<String> names;
     * }</blockquote>
     */
    public void styleGuideDo2() {
    }

    // violation below 'Javadoc content should start from the next line.'
    /** put on single line instead.
     */
    public void styleGuideDonts1() {
    }

    // violation 4 lines below 'Use snippet inline tag instead of 'pre' tag.'
    /**
     * The String below may interfere with the HTML.
     *
     * <blockquote><pre>
     *     List<String> names;
     * </pre></blockquote>
     */
    public void styleGuideDonts2() {
    }
}
