/*
UnnecessaryNullCheckWithInstanceOf


*/

// non-compiled with javac: Compilable with Java25

void main() {
    Object obj = "test";
    // violation below 'Unnecessary nullity check with instanceof operator in conditional expression.'
    if (obj != null && obj instanceof String) {
        System.out.println(obj);
    }
}
