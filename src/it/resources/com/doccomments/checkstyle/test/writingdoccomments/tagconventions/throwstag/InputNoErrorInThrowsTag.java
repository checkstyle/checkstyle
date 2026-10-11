package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.throwstag;

/**
 * Input for {@code Error} in {@code @throws} tag examples.
 */
public class InputNoErrorInThrowsTag {

    /**
     * Creates a sample instance.
     */
    public InputNoErrorInThrowsTag() {
    }

    // violation 4 lines below "Error type 'StackOverflowError' should not be documented"
    /**
     * Calculates recursive depth.
     *
     * @throws StackOverflowError if recursion is too deep
     */
    public void calculateDepthWarn() {
    }

    /**
     * Calculates recursive depth.
     */
    public void calculateDepthGood() {
    }

    // violation 4 lines below "Error type 'Error' should not be documented in '@exception' tag."
    /**
     * Resets runtime state.
     *
     * @exception Error if reset fails
     */
    public void resetStateWarn() {
    }

    /**
     * Resets runtime state.
     *
     * @exception IllegalStateException if reset fails
     */
    public void resetStateGood() {
    }

    /**
     * Publishes a failure explicitly.
     *
     * @throws AssertionError if publication fails
     */
    public void publishFailureGood() {
        throw new AssertionError("explicit failure");
    }

}
