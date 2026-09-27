/*
NoGetMessageInThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageinthrow;

import java.io.IOException;

class InputNoGetMessageInThrowNonMethodCalls {
    void method() throws IOException {
        try {
            throw new IOException();
        }
        catch (IOException ex) {
            int x = 5;
            String msg = "error";
            boolean flag = true;
        }
    }
}
