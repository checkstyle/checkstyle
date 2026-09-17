package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.paramtag;

/**
 * Input for correct {@code @param} tag examples.
 */
public class InputCorrectParamTag {

    /**
     * Creates a sample instance.
     */
    public InputCorrectParamTag() {
    }

    /**
     * Tests whether a character should be observed.
     *
     * @param ch the character to be tested
     * @param observer the image observer to be notified
     */
    public void observe(char ch, Object observer) {
    }

    /**
     * Moves to the supplied coordinates.
     *
     * @param x the x-coordinate, measured in pixels
     * @param y the y-coordinate, measured in pixels
     */
    public void move(int x, int y) {
    }

    /**
     * Accepts a generic value.
     *
     * @param <T> the value type
     * @param value the value to accept
     */
    public <T> void accept(T value) {
    }

}
