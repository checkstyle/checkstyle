package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.multipletags;

/**
 * Input for multiple {@code @see} tag order examples.
 */
public class InputJavadocSeeTagOrder {
    // violation 6 lines below '@see tag '#InputJavadocSeeTagOrder().*'
    /**
     * Invalid: local method appears before local constructor.
     *
     * @see #field
     * @see #getName()
     * @see #InputJavadocSeeTagOrder()
     */
    private void referencesWarn() {
    }

    /**
     * Valid: local field, constructor, then method.
     *
     * @see #field
     * @see #InputJavadocSeeTagOrder()
     * @see #getName()
     */
    private void referencesGood() {
    }

    /**
     * Creates a sample instance.
     */
    public InputJavadocSeeTagOrder() {
    }

    /**
     * Sample field.
     */
    private String field;

    /**
     * Returns the name.
     *
     * @return the name
     */
    private String getName() {
        return field;
    }
}
