package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.customtagsandannotations;

/**
 * Input for incorrect custom tags and annotations examples.
 */
public class InputIncorrectCustomTagsAndAnnotations {

    /**
     * Creates a sample instance.
     */
    public InputIncorrectCustomTagsAndAnnotations() {
    }

    // violation 4 lines below 'Must include both @java.lang.Deprecated annotation and @deprecated'
    /**
     * Uses the old API without the Javadoc tag.
     */
    @Deprecated
    public void annotationOnly() {
    }

    // violation 6 lines below 'Must include both @java.lang.Deprecated annotation and @deprecated'
    /**
     * Uses the old API without the annotation.
     *
     * @deprecated Use {@link #annotationOnly()} instead.
     */
    public void tagOnly() {
    }

}
