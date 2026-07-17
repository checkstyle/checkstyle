package com.doccomments.checkstyle.test.writingdoccomments.documentingexceptionswiththrowstag
        .exceptionstodocument;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Input for correct exceptions-to-document examples.
 */
public class InputCorrectExceptionsToDocument {

    /**
     * Creates a sample instance.
     */
    public InputCorrectExceptionsToDocument() {
    }

    /**
     * Reads one byte from the source.
     *
     * @return byte value
     * @throws IOException if reading fails
     */
    public int readByte() throws IOException {
        return 1;
    }

    /**
     * Opens the source file.
     *
     * @throws FileNotFoundException if the source file is missing
     */
    public void openSource() throws FileNotFoundException {
    }

    /**
     * Closes the source file.
     *
     * @exception IOException if closing fails
     */
    public void closeSource() throws IOException {
    }

    /**
     * Stores the supplied value.
     *
     * @param value value to store
     * @throws IOException if storing fails
     */
    public void store(String value) throws IOException {
    }
}
