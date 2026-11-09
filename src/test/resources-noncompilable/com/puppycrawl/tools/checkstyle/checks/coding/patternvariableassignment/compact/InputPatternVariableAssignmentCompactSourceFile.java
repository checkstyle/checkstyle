/*
PatternVariableAssignment


*/

// non-compiled with javac: Compilable with Java25

Object value = "text";

boolean isText = value instanceof String text;

record Pair(Object first, Object second) {
}

void reassign(Object obj) {
    if (obj instanceof String s) {
        s = "hello"; // violation 'Assignment of pattern variable 's' is not allowed.'
        System.out.println(s);
    }
    if (obj instanceof Integer number) {
        System.out.println(number);
    }
}

void main() {
    if (value instanceof Pair(String first, Object second)) {
        first = "changed"; // violation 'Assignment of pattern variable 'first' is not allowed.'
        System.out.println(first + second);
    }
}

class Nested {
    void nestedMethod(Object obj) {
        if (!(obj instanceof String s)) {
            return;
        }
        s = "after"; // violation 'Assignment of pattern variable 's' is not allowed.'
        System.out.println(s);
    }
}
