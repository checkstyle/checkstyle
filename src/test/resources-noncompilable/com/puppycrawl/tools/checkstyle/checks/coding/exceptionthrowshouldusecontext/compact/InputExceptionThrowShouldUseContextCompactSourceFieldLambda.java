/*
ExceptionThrowShouldUseContext


*/

// non-compiled with javac: Compilable with Java25

String field = "context";

Runnable withoutContext = () -> {
    try {
        Thread.sleep(10);
    }
    catch (InterruptedException ex) {
        // violation below 'Exception throw should always use context variables.'
        throw new IllegalStateException("interrupted", ex);
    }
};

Runnable withFieldContext = () -> {
    try {
        Thread.sleep(10);
    }
    catch (InterruptedException ex) {
        throw new IllegalStateException("interrupted " + field, ex);
    }
};

void main() {
    withoutContext.run();
    withFieldContext.run();
}
