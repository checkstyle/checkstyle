/*
NoGetMessageInCatchThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageincatchthrow;

import java.io.IOException;

class InputNoGetMessageInCatchThrowCorrect {

    void method1() {
        // ok provides context without redundant message
        try {
            throw new IOException();
        } catch (IOException ex) {
            throw new RuntimeException("Error processing file", ex);
        }
    }
}
