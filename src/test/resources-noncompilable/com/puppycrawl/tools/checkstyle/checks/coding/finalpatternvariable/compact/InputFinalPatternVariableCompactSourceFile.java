/*
FinalPatternVariable


*/

// non-compiled with javac: Compilable with Java25

void main() {
    Object o = "test";
    // violation below "Pattern variable 's' should be declared final."
    if (o instanceof String s) {
        System.out.println(s);
    }
}
