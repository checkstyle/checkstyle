package com.openjdk.checkstyle.test.chapterformatting.rulejavadoc;

// violation first line 'Header mismatch'

public class InputJavadocFive {

    // violation 2 lines below 'Use code or snippet inline tags instead of 'code' tag.'
    /**
     * Returns <code>true</code> if the value is valid.
     */
    public void methodBad1() {
    }

    /**
     * Returns {@code true} if the value is valid.
     */
    public void methodGood1() {
    }

    // violation 4 lines below 'Use snippet inline tag instead of 'code' tag.'
    /**
     * Checks the condition.
     *
     * <code>
     *     if (condition) {
     *         return true;
     *     }
     * </code>
     */
    public void methodBad2() {
    }

    // violation 4 lines below 'Use code or snippet inline tags instead of 'pre' tag.'
    /**
     * Initializes the value.
     *
     * <pre> int s = 10;</pre>
     */
    public void methodBad3() {
    }

    // violation 4 lines below 'Use snippet inline tag instead of 'pre' tag.'
    /**
     * Checks the condition.
     *
     * <pre>
     *     if (condition) {
     *         return true;
     *     }
     * </pre>
     */
    public void methodBad4() {
    }

    /**
     * Checks the condition.
     *
     * {@snippet :
     *     if (condition) {
     *         return true;
     *     }
     * }
     */
    public void methodGood2() {
    }

    /**
     * Closes the block with <code>}</code>.
     */
    public void methodUnbalancedBraces() {
    }

    /**
     * Shows the comment format.
     *
     * <pre>
     * * This is some text.
     * </pre>
     */
    public void methodContentStartsWithStar() {
    }

    // violation 4 lines below 'Use snippet inline tag instead of 'pre' tag.'
    /**
     * Shows nested tags.
     *
     * <pre>
     *     <code>int a = 1;</code>
     * </pre>
     */
    public void methodNestedTags() {
    }

}
