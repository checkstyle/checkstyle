package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.sincetag;

/**
 * Input for correct {@code @since} tag examples.
 *
 * @since 1.0
 */
public class InputCorrectSinceTag {

    /**
     * Stores the initial value.
     *
     * @since 1.0
     */
    private int value;

    /**
     * Creates a sample instance.
     *
     * @since 1.0
     */
    public InputCorrectSinceTag() {
    }

    /**
     * Stores a value.
     *
     * @param value value to store
     * @since 1.1
     */
    public void store(int value) {
        this.value = value;
    }

    /**
     * Returns the stored value.
     *
     * @return stored value
     * @since Java SE 11
     */
    public int getValue() {
        return value;
    }

}
