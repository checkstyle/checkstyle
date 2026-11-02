/*
LocalFinalVariableName
format = (default)^([a-z][a-zA-Z0-9]*|_)$
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, RESOURCE


*/

// non-compiled with javac: Compilable with Java25

void main() {
    final int good = 0;
    final int Bad = 0; // violation 'Name 'Bad' must match pattern'
    int notFinal = 0;
    try (java.io.StringReader Reader = new java.io.StringReader("")) {
        // violation above 'Name 'Reader' must match pattern'
    }
    catch (final Exception E) { // violation 'Name 'E' must match pattern'
    }
}
