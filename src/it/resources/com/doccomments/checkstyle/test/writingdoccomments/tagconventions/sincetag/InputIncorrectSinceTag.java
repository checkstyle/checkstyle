package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.sincetag;

/**
 * Input for incorrect {@code @since} tag examples.
 */
public class InputIncorrectSinceTag {

    /**
     * Creates a sample instance.
     */
    public InputIncorrectSinceTag() {
    }

    // violation 4 lines below 'At-clause should have a non-empty description.'
    /**
     * Stores a value.
     *
     * @since
     */
    private int value;

    // violation 4 lines below 'At-clause should have a non-empty description.'
    /**
     * Updates a value.
     *
     * @since
     */
    public void update() {
        value++;
    }

}
