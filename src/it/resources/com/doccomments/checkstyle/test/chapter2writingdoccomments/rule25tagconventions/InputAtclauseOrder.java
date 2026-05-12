package com.doccomments.checkstyle.test.chapter2writingdoccomments.rule25tagconventions;

/**
 * Input for AtclauseOrderCheck examples.
 */
public class InputAtclauseOrder {

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

    /**
     * Invalid: @return appears before @param.
     *
     * @return processed value
     * @param value value to process // violation
     */
    public int invalidReturnBeforeParam(int value) {
        return value;
    }

    /**
     * Invalid: @since appears before @see.
     *
     * @param value value to process
     * @return processed value
     * @since 1.0
     * @see Integer // violation
     */
    public int invalidSinceBeforeSee(int value) {
        return value;
    }

    /**
     * Valid: @throws appears before @exception.
     *
     * @throws IllegalArgumentException when value is negative
     * @exception IllegalStateException when state is invalid
     */
    public void validThrowsBeforeException(int value) {
        if (value < 0) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Invalid: @deprecated must come after @since.
     *
     * @deprecated use {@link #validMethodOrder(int)} instead
     * @since 1.0 // violation
     */
    @Deprecated
    public void invalidDeprecatedBeforeSince() {
    }
}
