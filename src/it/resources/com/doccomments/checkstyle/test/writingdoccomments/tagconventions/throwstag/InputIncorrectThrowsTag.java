package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.throwstag;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Input for incorrect {@code @throws} tag examples.
 */
public class InputIncorrectThrowsTag {

    /**
     * Creates a sample instance.
     */
    public InputIncorrectThrowsTag() {
    }

    /**
     * Reads one byte without documenting the checked exception.
     *
     * @return byte value
     */
    // violation below 'Expected @throws tag for 'IOException'.'
    public int missingThrowsTag() throws IOException {
        return 1;
    }

    /**
     * Opens a file without documenting the checked exception.
     */
    // violation below 'Expected @throws tag for 'FileNotFoundException'.'
    public void missingThrowsTagOnVoidMethod() throws FileNotFoundException {
    }
}
