package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.requiredtags;

/**
 * Input for JavadocMethodCheck required tags examples.
 */
public class InputRequiredTags {

    private final String name;

    /**
     * Creates a sample instance.
     *
     * @param name name to store
     */
    public InputRequiredTags(String name) {
        this.name = name;
    }

    /**
     * Returns the stored name.
     *
     * @return stored name
     */
    public String getName() {
        return name;
    }

    /**
     * Calculates a sum from both values.
     *
     * @param first first value
     * @param second second value
     * @return sum of values
     */
    public int sum(int first, int second) {
        return first + second;
    }

    /**
     * Records the supplied text.
     *
     * @param text text to record
     */
    public void record(String text) {
    }

    /**
     * Validates and returns the supplied value.
     *
     * @param value value to validate
     * @return validated value
     * @throws IllegalArgumentException if value is negative
     */
    public int validate(int value) throws IllegalArgumentException {
        if (value < 0) {
            throw new IllegalArgumentException();
        }
        return value;
    }

    // violation 4 lines below '@return tag should be present and have description.'
    /**
     * Returns the current size.
     */
    public int missingReturnTag() {
        return 1;
    }

    // violation 4 lines below 'Expected @param tag for 'value'.'
    /**
     * Updates the current value.
     */
    public void missingParamTag(String value) {
    }
}
