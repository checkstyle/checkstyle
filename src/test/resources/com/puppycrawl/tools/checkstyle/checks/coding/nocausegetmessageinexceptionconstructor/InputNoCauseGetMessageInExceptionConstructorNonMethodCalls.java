/*
NoCauseGetMessageInExceptionConstructor


*/

package com.puppycrawl.tools.checkstyle.checks.coding.nocausegetmessageinexceptionconstructor;

import java.io.IOException;

class InputNoCauseGetMessageInExceptionConstructorNonMethodCalls {
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
