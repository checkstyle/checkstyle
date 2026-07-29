package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.customtagsandannotations;

/**
 * Input for correct custom tags and annotations examples.
 */
public class InputCorrectCustomTagsAndAnnotations {

    /**
     * Creates a sample instance.
     */
    public InputCorrectCustomTagsAndAnnotations() {
    }

    /**
     * Uses the current API.
     */
    public void currentApi() {
    }

    /**
     * Uses the old API.
     *
     * @deprecated Use {@link #currentApi()} instead.
     */
    @Deprecated
    public void oldApi() {
    }

    /**
     * Uses another old API.
     *
     * @deprecated Use {@link #currentApi()} instead.
     */
    @java.lang.Deprecated
    public void anotherOldApi() {
    }

}
