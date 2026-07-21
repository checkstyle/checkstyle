package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.orderoftags;

/**
 * Input for AtclauseOrderCheck examples.
 */
public class InputAtclauseOrder {

    /**
     * Creates a sample instance.
     */
    public InputAtclauseOrder() {
    }

    /**
     * Valid: tags are in default order.
     *
     * @param value value to process
     * @return processed value
     * @throws IllegalArgumentException when value is negative
     * @see String
     * @since 1.0
     */
    public int validMethodOrder(int value) {
        return value;
    }

    // violation 5 lines below 'Block tags have to appear in the order'
    /**
     * Invalid: @return appears before @param.
     *
     * @return processed value
     * @param value value to process
     */
    public int invalidReturnBeforeParam(int value) {
        return value;
    }

    // violation 7 lines below 'Block tags have to appear in the order'
    /**
     * Invalid: @since appears before @see.
     *
     * @param value value to process
     * @return processed value
     * @since 1.0
     * @see Integer
     */
    public int invalidSinceBeforeSee(int value) {
        return value;
    }

    /**
     * Valid: @throws appears before @exception.
     *
     * @param value value to process
     * @throws IllegalArgumentException when value is negative
     * @exception IllegalStateException when state is invalid
     */
    public void validThrowsBeforeException(int value) {
        if (value < 0) {
            throw new IllegalArgumentException();
        }
    }

    // violation 5 lines below 'Block tags have to appear in the order'
    /**
     * Invalid: @deprecated must come after @since.
     *
     * @deprecated use {@link #validMethodOrder(int)} instead
     * @since 1.0
     */
    @Deprecated
    public void invalidDeprecatedBeforeSince() {
    }
}
