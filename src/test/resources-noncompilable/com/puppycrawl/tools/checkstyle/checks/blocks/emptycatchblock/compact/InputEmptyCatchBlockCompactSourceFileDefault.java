/*
EmptyCatchBlock
exceptionVariableName = (default)^$
commentFormat = (default).*

*/

// non-compiled with javac: Compilable with Java25

void checkCatches() {
    try {
        throw new RuntimeException();
    } catch (RuntimeException e) {
    } // violation above 'Empty catch block'
    try {
        throw new RuntimeException();
    } catch (RuntimeException e) {
        // ignored
    }
    try {
        throw new RuntimeException();
    } catch (RuntimeException e) {
        System.out.println(e);
    }
}

void main() {
}
