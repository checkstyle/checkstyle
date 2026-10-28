/*
EmptyCatchBlock
exceptionVariableName = expected|ignore
commentFormat = This is expected

*/

// non-compiled with javac: Compilable with Java25

void checkCatches() {
    try {
        throw new RuntimeException();
    } catch (RuntimeException expected) {
    }
    try {
        throw new RuntimeException();
    } catch (RuntimeException e) {
    } // violation above 'Empty catch block'
    try {
        throw new RuntimeException();
    } catch (RuntimeException e) {
        // This is expected
    }
    try {
        throw new RuntimeException();
    } catch (RuntimeException e) {
        // some other comment
    } // violation 2 lines above 'Empty catch block'
    try {
        throw new RuntimeException();
    } catch (RuntimeException ignore) {
        // some other comment
    }
}

void main() {
}
