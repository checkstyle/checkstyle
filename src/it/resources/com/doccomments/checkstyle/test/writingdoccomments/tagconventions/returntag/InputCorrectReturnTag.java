package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.returntag;

/**
 * Input for correct {@code @return} tag examples.
 */
public class InputCorrectReturnTag {

    private int storedValue;

    /**
     * Creates a sample instance.
     */
    public InputCorrectReturnTag() {
    }

    /**
     * Returns the stored value.
     *
     * @return stored value
     */
    public int getStoredValue() {
        return storedValue;
    }

    /**
     * {@return the stored value}
     */
    public int getStoredValueInline() {
        return storedValue;
    }

    /**
     * Updates the stored value.
     *
     * @param value value to store
     */
    public void setStoredValue(int value) {
        storedValue = value;
    }

    /**
     * Returns a value within the supported range.
     *
     * @param value value to normalize
     * @return zero when the value is negative; otherwise, the supplied value
     */
    public int normalize(int value) {
        return Math.max(0, value);
    }
}
