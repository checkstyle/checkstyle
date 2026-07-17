package com.doccomments.checkstyle.test.writingdoccomments.documentingexceptionswiththrowstag
        .exceptionstodocument;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Input for incorrect exceptions-to-document examples.
 */
public class InputIncorrectExceptionsToDocument {

    /**
     * Creates a sample instance.
     */
    public InputIncorrectExceptionsToDocument() {
    }

    // violation 6 lines below 'Expected @throws tag for 'IOException'.'
    /**
     * Reads one byte without documenting the checked exception.
     *
     * @return byte value
     */
    public int missingThrowsTag() throws IOException {
        return 1;
    }

    // violation 4 lines below 'Expected @throws tag for 'FileNotFoundException'.'
    /**
     * Opens a file without documenting the checked exception.
     */
    public void missingThrowsTagOnVoidMethod() throws FileNotFoundException {
    }
}
