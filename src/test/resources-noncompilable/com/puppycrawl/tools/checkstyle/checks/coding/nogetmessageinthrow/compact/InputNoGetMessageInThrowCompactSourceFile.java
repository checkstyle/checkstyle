/*
NoGetMessageInThrow


*/

// non-compiled with javac: Compilable with Java25

void main() {
    try {
        throw new RuntimeException();
    } catch (RuntimeException ex) {
        throw new RuntimeException("Error: " + ex.getMessage()); // violation 'Avoid using'
    }
}
