/*
JavadocParamOrder
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

class InputJavadocParamOrderSupplemental {

    /**
     * Correct interface type parameter order.
     *
     * @param <K> key type
     * @param <V> value type
     */
    interface InterfaceOrder<K, V> {
    }

    /**
     * Extra type parameter tag is ignored.
     *
     * @param <X> unknown type parameter
     */
    void extraTypeParamTag() {
    }

    /**
     * Param tag without a name is ignored.
     *
     * @param
     */
    void paramTagWithoutName() {
    }

    /**
     * Receiver parameter is ignored.
     *
     * @param name name
     * @param role role
     */
    void receiverParameter(InputJavadocParamOrderSupplemental this, String name,
            String role) {
    }

}
