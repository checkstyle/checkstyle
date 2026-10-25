/*
FinalParameters
ignorePrimitiveTypes = true
ignoreUnnamedParameters = (default)true
tokens = METHOD_DEF, CTOR_DEF, LITERAL_CATCH, FOR_EACH_CLAUSE

*/

// non-compiled with javac: Compilable with Java25

void main() {
}

void primitives(byte octet, short small, int number, long large,
        float fraction, double decimal, boolean flag, char letter) {
}

void array(int[] numbers) { // violation 'Parameter numbers should be final.'
}

void varargs(int... numbers) { // Primitive varargs are ignored, unlike array parameters.
}

void reference(String text) { // violation 'Parameter text should be final.'
}

void finalParameters(final int[] numbers, final String text) {
}

void blocks() {
    try {
        throw new Exception();
    }
    catch (Exception _) {
        // Unnamed parameters are ignored by default.
    }
    for (String _ : new String[0]) {
        // Unnamed reference parameters are ignored, not just primitives.
    }
    for (int number : new int[0]) {
        // Primitive parameters are ignored with ignorePrimitiveTypes=true.
    }
    for (String value : new String[0]) { // violation 'Parameter value should be final.'
    }
}

class Nested {
    Nested(int number) {
    }

    Nested(String text) { // violation 'Parameter text should be final.'
    }
}
