/*
PatternVariableName
format = (default)^([a-z][a-zA-Z0-9]*|_)$


*/

// non-compiled with javac: Compilable with Java25

void main() {
    Object first = "text";
    if (first instanceof String good) {
        System.out.println(good);
    }
    if (first instanceof String Bad) { // violation 'Name 'Bad' must match pattern'
        System.out.println(Bad);
    }
    Object second = Integer.valueOf(42);
    if (second instanceof Integer BAD_NAME) { // violation 'Name 'BAD_NAME' must match pattern'
        System.out.println(BAD_NAME);
    }
}
