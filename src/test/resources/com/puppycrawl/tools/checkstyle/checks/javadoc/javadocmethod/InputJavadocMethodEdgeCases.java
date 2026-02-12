/*
JavadocMethod
validateThrows = true
allowedAnnotations = (default)Override
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodEdgeCases {

    /** Short javadoc on same line. */
    public void methodWithShortJavadoc() {
    }

    /**
     * Javadoc with exact column position.
     * @throws Exception never
     */
    public void methodWithException() throws Exception {
    }

    /** Inline javadoc. @param x value */
    // violation below 'Expected @param tag for 'x''
    public void methodWithParam(int x) {
    }

    /**
     * Multi-line javadoc
     * with several lines
     * @return value
     */
    public int methodWithReturn() {
        return 0;
    }

    /* Data loading methods. */

    /**
     * Javadoc before annotation.
     * @param val the value
     */
    @SuppressWarnings("unused")
    public void methodWithAnnotation(String val) {
    }

    /** */
    /** Simple constructor.
     * @param words raw words
     */
    InputJavadocMethodEdgeCases(final int[] words) {
    }

    /**
     * Non-javadoc comment should be ignored.
     */
    @Deprecated
    public void methodWithDeprecated() {
    }

    /* Not a javadoc comment */
    public void methodWithRegularComment() {
    }

    /**
     * Method with missing throws tag.
     */
    // violation below 'Expected @throws tag for 'IllegalArgumentException''
    public void methodMissingThrows() throws IllegalArgumentException {
    }

    /**
     * Javadoc with multiple annotations.
     * @param x value
     */
    @Deprecated
    @SuppressWarnings("unused")
    public void methodWithMultipleAnnotations(int x) {
    }

        /** Javadoc indented with spaces at column 8. */
        public void methodIndented() {
        }

    /**
     * Method with generic type parameter.
     * @param <T> the type parameter
     * @param value the value
     */
    public <T> void methodWithGeneric(T value) {
    }

    /**
     * Method with missing generic parameter documentation.
     * @param value the value
     */
    // violation below 'Expected @param tag for '<E>''
    public <E> void methodMissingGenericParam(E value) {
    }

    /**
     * Method with multiple generic parameters.
     * @param <K> the key type
     * @param <V> the value type
     * @param key the key
     * @param value the value
     */
    public <K, V> void methodWithMultipleGenerics(K key, V value) {
    }
}


