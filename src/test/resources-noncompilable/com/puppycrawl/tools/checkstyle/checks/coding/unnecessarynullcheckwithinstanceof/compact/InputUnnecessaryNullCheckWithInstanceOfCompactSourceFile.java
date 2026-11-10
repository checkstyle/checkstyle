/*
UnnecessaryNullCheckWithInstanceOf

*/

// non-compiled with javac: Compilable with Java25

void test(Object obj) {
    if (obj != null && obj instanceof String) { // violation 'Unnecessary nullity check'
        String s = (String) obj;
    }
}

void main() {
}
