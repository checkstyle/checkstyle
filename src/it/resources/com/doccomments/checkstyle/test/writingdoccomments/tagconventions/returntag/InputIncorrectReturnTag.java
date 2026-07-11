package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.returntag;

/**
 * Input for incorrect {@code @return} tag examples.
 */
public class InputIncorrectReturnTag {

    /**
     * Creates a sample instance.
     */
    public InputIncorrectReturnTag() {
    }

    // violation 4 lines below '@return tag should be present and have description.'
    /**
     * Returns a value without documenting it.
     */
    public int missingReturnTag() {
        return 1;
    }

    // violation 6 lines below '@return tag should be present and have description.'
    /**
     * Returns a value with an empty description.
     *
     * @return
     */
    public int emptyReturnDescription() {
        return 2;
    }
}
