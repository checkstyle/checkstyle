package com.doccomments.checkstyle.test.writingdoccomments.formatofdoccomment.notes;

/**
 * Input for correct notes examples.
 */
public class InputCorrectNotes {

    /** Stored value. */
    private int value;

    /**
     * Creates a sample instance.
     */
    public InputCorrectNotes() {
    }

    /**
     * Returns the stored value.
     *
     * <p>This paragraph remains part of the description.
     *
     * @return stored value
     */
    public int getValue() {
        return value;
    }

    /**
     * Updates the stored value.
     *
     * @param value value to store
     *     when callers need a custom value
     */
    public void setValue(int value) {
        this.value = value;
    }

}
