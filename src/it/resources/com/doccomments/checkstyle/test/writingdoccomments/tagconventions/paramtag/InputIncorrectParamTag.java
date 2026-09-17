package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.paramtag;

/**
 * Input for incorrect {@code @param} tag examples.
 */
public class InputIncorrectParamTag {

    /**
     * Creates a sample instance.
     */
    public InputIncorrectParamTag() {
    }

    // violation 5 lines below 'Unused @param tag for 'String'.'
    // violation 6 lines below 'Expected @param tag for 'value'.'
    /**
     * Stores a value.
     *
     * @param String value to store
     */
    public void dataTypeAsParamName(String value) {
    }

    // violation 4 lines below 'At-clause should have a non-empty description.'
    /**
     * Updates a value.
     *
     * @param value
     */
    public void emptyDescription(String value) {
    }

    // violation 4 lines below 'Expected @param tag for 'name'.'
    /**
     * Renames a value.
     */
    public void missingParamTag(String name) {
    }

}
