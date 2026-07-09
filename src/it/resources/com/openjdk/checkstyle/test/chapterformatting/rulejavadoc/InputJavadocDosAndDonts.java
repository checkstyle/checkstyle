package com.openjdk.checkstyle.test.chapterformatting.rulejavadoc;

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

    // no violation until https://github.com/checkstyle/checkstyle/issues/18570
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
