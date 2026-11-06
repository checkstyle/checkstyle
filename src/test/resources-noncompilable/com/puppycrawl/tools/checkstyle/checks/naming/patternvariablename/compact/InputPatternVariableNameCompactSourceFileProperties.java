/*
PatternVariableName
format = ^[A-Z][a-zA-Z0-9]*$


*/

// non-compiled with javac: Compilable with Java25

void main() {
    Object first = "text";
    if (first instanceof String Text) {
        System.out.println(Text);
    }
    if (first instanceof String lower) { // violation 'Name 'lower' must match pattern'
        System.out.println(lower);
    }
}
