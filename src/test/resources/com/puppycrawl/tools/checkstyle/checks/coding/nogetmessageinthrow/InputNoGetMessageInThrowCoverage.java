/*
NoGetMessageInThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageinthrow;

import java.io.IOException;

class InputNoGetMessageInThrowCoverage {

    private final IOException stored = new IOException("stored");

    void rethrowWithoutNew() throws IOException {
        // ok - rethrow without new expression (literalNew is null)
        try {
            throw new IOException();
        }
        catch (IOException ex) {
            throw ex;
        }
    }

    void getMessageOnNonCatchVariable() throws IOException {
        // ok - getMessage on a field, not the catch parameter
        try {
            throw new IOException();
        }
        catch (IOException ex) {
            throw new IOException(stored.getMessage());
        }
    }

    void differentTypeWithGetMessage() throws IOException {
        // ok - getMessage on catch var but thrown type differs
        try {
            throw new IOException();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    void sameTypeWithToString() throws IOException {
        // ok - same thrown type but toString, not getMessage
        try {
            throw new IOException();
        }
        catch (IOException ex) {
            throw new IOException(ex.toString());
        }
    }
}

