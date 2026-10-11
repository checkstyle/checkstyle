package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.deprecatedtag;

/**
 * Input for incorrect {@code @deprecated} tag examples.
 */
public class InputIncorrectDeprecatedTag {

    /**
     * Creates a sample instance.
     */
    public InputIncorrectDeprecatedTag() {
    }

    // violation 4 lines below 'At-clause should have a non-empty description.'
    /**
     * Uses the old API.
     *
     * @deprecated
     */
    @Deprecated
    public void oldApi() {
    }

}
