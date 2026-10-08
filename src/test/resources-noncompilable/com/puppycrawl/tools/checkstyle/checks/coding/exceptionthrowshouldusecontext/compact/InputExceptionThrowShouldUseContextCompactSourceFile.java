/*
ExceptionThrowShouldUseContext


*/

// non-compiled with javac: Compilable with Java25

import java.io.IOException;

void main() {
}

String field = "context";

void method1(String someParameter) {
    try {
        riskyOperation();
    }
    catch (IOException ex1) {
        // violation below 'Exception throw should always use context variables.'
        throw new IllegalStateException("unable to process ", ex1);
    }
    catch (Exception ex2) {
        throw new IllegalStateException("unable to process " + someParameter, ex2);
    }
}

void method2() {
    try {
        riskyOperation();
    }
    catch (Exception ex) {
        throw new IllegalStateException("unable to process " + field, ex);
    }
}

void method3() {
    String local = "local";
    try {
        riskyOperation();
    }
    catch (Exception ex) {
        throw new IllegalStateException("unable to process " + local, ex);
    }
}

void riskyOperation() throws IOException {
}

class Nested {
    void method(String param) {
        try {
            riskyOperation();
        }
        catch (Exception ex) {
            // violation below 'Exception throw should always use context variables.'
            throw new IllegalStateException("unable to process ", ex);
        }
    }
}
