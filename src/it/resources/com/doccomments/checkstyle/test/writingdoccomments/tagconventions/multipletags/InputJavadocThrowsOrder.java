package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.multipletags;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

/**
 * Input for multiple {@code @throws} tag order examples.
 */
public class InputJavadocThrowsOrder {

    /**
     * Creates a sample instance.
     */
    public InputJavadocThrowsOrder() {
    }

    // violation 5 lines below '@throws tag for 'IOException' should be placed'
    /**
     * Reads from storage.
     *
     * @throws SQLException if storage cannot be queried
     * @throws IOException if input cannot be read
     */
    public void readWarn() throws IOException, SQLException {
    }

    /**
     * Reads from storage.
     *
     * @throws IOException if input cannot be read
     * @throws SQLException if storage cannot be queried
     */
    public void readGood() throws IOException, SQLException {
    }

    // violation 5 lines below '@exception tag for 'InterruptedException' should be placed'
    /**
     * Waits for completion.
     *
     * @exception TimeoutException if the operation times out
     * @exception InterruptedException if interrupted while waiting
     */
    public void waitWarn() throws InterruptedException, TimeoutException {
    }

    /**
     * Waits for completion.
     *
     * @exception InterruptedException if interrupted while waiting
     * @exception TimeoutException if the operation times out
     */
    public void waitGood() throws InterruptedException, TimeoutException {
    }

}
