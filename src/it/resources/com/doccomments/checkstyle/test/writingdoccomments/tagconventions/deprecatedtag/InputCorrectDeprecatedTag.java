package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.deprecatedtag;

/**
 * Input for correct {@code @deprecated} tag examples.
 */
public class InputCorrectDeprecatedTag {

    /**
     * Creates a sample instance.
     */
    public InputCorrectDeprecatedTag() {
    }

    /**
     * Uses the current API.
     */
    public void currentApi() {
    }

    /**
     * Uses the old API.
     *
     * @deprecated As of release 2.0, use {@link #currentApi()} instead.
     */
    @Deprecated
    public void oldApi() {
    }

}
