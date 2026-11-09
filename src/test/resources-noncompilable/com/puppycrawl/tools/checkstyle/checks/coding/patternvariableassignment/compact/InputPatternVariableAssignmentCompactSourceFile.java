/*
PatternVariableAssignment


*/

// non-compiled with javac: Compilable with Java25

void testMethod(Object obj) {
    if (obj instanceof String s) {
        s = "hello"; // violation "Assignment of pattern variable 's' is not allowed."
        System.out.println(s);
    }
    if (obj instanceof Integer i) {
        System.out.println(i);
    }
}

void main() {
    testMethod("test");
}
