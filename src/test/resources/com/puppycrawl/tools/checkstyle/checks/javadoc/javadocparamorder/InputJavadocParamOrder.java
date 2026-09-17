/*
JavadocParamOrder
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

class InputJavadocParamOrder {

    // violation 6 lines below '@param tag for .* should be in declaration order.'
    // violation 6 lines below '@param tag for .* should be in declaration order.'
    /**
     * Description.
     *
     * @param p2 parameter 2
     * @param p1 parameter 1
     * @param <T> type parameter
     * @param p3 parameter 3
     */
    public <T> void issueExample(String p1, String p2, T p3) {
    }

    /**
     * Missing parameter tag is ignored.
     *
     * @param a parameter a
     * @param b parameter b
     * @param d parameter d
     */
    void missingParamTag(int a, int b, int c, int d) {
    }

    /**
     * Extra parameter tag is ignored.
     *
     * @param a parameter a
     * @param b parameter b
     * @param c parameter c
     * @param x extra parameter
     * @param d parameter d
     */
    void extraParamTag(int a, int b, int c, int d) {
    }

    /**
     * Duplicate tag that does not move backward is ignored.
     *
     * @param a parameter a
     * @param b parameter b
     * @param c parameter c
     * @param c duplicate parameter c
     * @param d parameter d
     */
    void duplicateParamTag(int a, int b, int c, int d) {
    }

    // violation 7 lines below '@param tag for .* should be in declaration order.'
    /**
     * Duplicate tag that moves backward is a violation.
     *
     * @param a parameter a
     * @param b parameter b
     * @param c parameter c
     * @param a duplicate parameter a
     * @param d parameter d
     */
    void duplicateBackwardsParamTag(int a, int b, int c, int d) {
    }

    // violation 5 lines below '@param tag for .* should be in declaration order.'
    /**
     * Type parameter order follows the class declaration.
     *
     * @param <V> value type
     * @param <K> key type
     */
    static class TypeOrder<K, V> {
    }

    // violation 6 lines below '@param tag for .* should be in declaration order.'
    /**
     * Record type parameter must be documented before components.
     *
     * @param key key component
     * @param value value component
     * @param <T> value type
     */
    record RecordOrder<T>(T key, T value) {
    }

    record CompactOrder(String name, String address) {

        // violation 5 lines below '@param tag for .* should be in declaration order.'
        /**
         * Compact constructor uses record component order.
         *
         * @param address address component
         * @param name name component
         */
        CompactOrder {
        }
    }

    // violation 6 lines below '@param tag for .* should be in declaration order.'
    // violation 6 lines below '@param tag for .* should be in declaration order.'
    /**
     * Constructor type parameters are ordered before constructor parameters.
     *
     * @param p2 parameter 2
     * @param <T> type parameter
     * @param p1 parameter 1
     */
    <T> InputJavadocParamOrder(String p1, T p2) {
    }

}
