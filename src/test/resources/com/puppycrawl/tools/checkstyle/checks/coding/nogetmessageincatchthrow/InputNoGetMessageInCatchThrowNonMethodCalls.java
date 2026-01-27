/*
NoGetMessageInCatchThrow


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nogetmessageincatchthrow;

import java.io.IOException;

class InputNoGetMessageInCatchThrowNonMethodCalls {
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
