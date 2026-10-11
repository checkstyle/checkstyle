/*
SimplifyBooleanReturn


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

boolean isReady(boolean condition) {
    if (condition) { // violation 'Conditional logic can be removed.'
        return true;
    }
    else {
        return false;
    }
}
