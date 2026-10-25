/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = false
tokens = METHOD_DEF, CTOR_DEF, LITERAL_CATCH, FOR_EACH_CLAUSE, PATTERN_VARIABLE_DEF

*/

// non-compiled with javac: Compilable with Java25

void main() {
}

void catches() {
    try {
        throw new Exception();
    }
    catch (Exception _) { // violation 'Parameter _ should be final.'
    }
    try {
        throw new Exception();
    }
    catch (Exception exception) { // violation 'Parameter exception should be final.'
    }
    try {
        throw new Exception();
    }
    catch (final Exception exception) {
    }
}

void loops() {
    for (String _ : new String[0]) { // violation 'Parameter _ should be final.'
    }
    for (String value : new String[0]) { // violation 'Parameter value should be final.'
    }
    for (final String value : new String[0]) {
    }
}

void patterns(final Object value) {
    if (value instanceof String text) { // violation 'Parameter text should be final.'
    }
    if (value instanceof final String text) {
    }
}
