/*
LocalFinalVariableName
format = ^[A-Z][A-Z0-9]*$
tokens = VARIABLE_DEF, RESOURCE


*/

// non-compiled with javac: Compilable with Java25

void main() {
    final int GOOD = 0;
    final int bad = 0; // violation 'Name 'bad' must match pattern'
    try (java.io.StringReader reader = new java.io.StringReader("")) {
        // violation above 'Name 'reader' must match pattern'
    }
    catch (final Exception e) {
    }
}
