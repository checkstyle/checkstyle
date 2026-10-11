package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.multipletags;

/**
 * Input for multiple {@code @param} tag order examples.
 */
public class InputJavadocParamOrder {

    /**
     * Creates a sample instance.
     */
    public InputJavadocParamOrder() {
    }

    // violation 5 lines below '@param tag for 'customerName' should be in declaration order.'
    /**
     * Stores the customer details.
     *
     * @param accountId account identifier
     * @param customerName customer name
     */
    public void storeCustomerDetailsWarn(String customerName, String accountId) {
    }

    /**
     * Stores the customer details.
     *
     * @param customerName customer name
     * @param accountId account identifier
     */
    public void storeCustomerDetailsGood(String customerName, String accountId) {
    }

    // violation 5 lines below '@param tag for '<T>' should be in declaration order.'
    /**
     * Converts the value.
     *
     * @param value value to convert
     * @param <T> value type
     * @return converted value
     */
    public <T> T convertWarn(T value) {
        return value;
    }

    /**
     * Converts the value.
     *
     * @param <T> value type
     * @param value value to convert
     * @return converted value
     */
    public <T> T convertGood(T value) {
        return value;
    }

}
