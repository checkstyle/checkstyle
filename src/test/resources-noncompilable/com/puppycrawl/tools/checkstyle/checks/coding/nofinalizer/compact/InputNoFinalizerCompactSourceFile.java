/*
NoFinalizer


*/

// non-compiled with javac: Compilable with Java25

// violation below 'Avoid using finalizer method.'
protected void finalize() {
}

void finalize(String value) {
}

void main() {
}
