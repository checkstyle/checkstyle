package com.doccomments.checkstyle.test.writingdoccomments.formatofdoccomment;

/**
 * Input with doc comments on public API declarations.
 */
public class InputCorrectFormatOfDocComment {

    /** Public value. */
    public static final int VALUE = 1;

    /** Stored text. */
    private final String text;

    /**
     * Creates a sample instance.
     *
     * @param text text to store
     */
    public InputCorrectFormatOfDocComment(String text) {
        this.text = text;
    }

    /**
     * Returns the stored text.
     *
     * @return stored text
     */
    public String getText() {
        return text;
    }

    /**
     * Updates the current value.
     *
     * @param value value to use
     */
    public void update(int value) {
    }

}
