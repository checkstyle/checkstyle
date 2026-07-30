package com.doccomments.checkstyle.test.writingdoccomments.formatofdoccomment.notes;

// violation below 'Block comment has incorrect indentation level'
 /**
  * Input for incorrect notes examples.
  */
public class InputIncorrectNotes {

    /** Stored value. */
    private int value;

    // violation 2 lines below 'Leading asterisk has incorrect indentation level'
    /**
      * Creates a sample instance.
     */
    public InputIncorrectNotes() {
    }

    // violation 3 lines below 'Javadoc tag '@return' should be preceded with an empty line.'
    /**
     * Returns the stored value.
     * @return stored value
     */
    public int getValue() {
        return value;
    }

    // violation 5 lines below 'Line continuation have incorrect indentation level'
    /**
     * Updates the stored value.
     *
     * @param value value to store
     * next line is not indented as tag continuation text
     */
    public void setValue(int value) {
        this.value = value;
    }

    // violation 2 lines below 'Line is longer than 80 characters'
    /**
     * This summary is intentionally longer than the configured eighty character Javadoc limit.
     */
    public void longDescription() {
    }

}
