/*
UnnecessaryParentheses
tokens = EXPR, ASSIGN


*/

// non-compiled with javac: Compilable with Java25
void main() {
    Object value = consume((1 + 2)); // violation 'around assignment right-hand side'
    consume((1 + 2)); // violation 'around expression'
}

Object anonymousField = new Object() {
    void method() {
        consume((1 + 2)); // violation 'around expression'
        Object assigned = consume((1 + 2));
        // violation above 'around assignment right-hand side'
    }
};

java.util.function.Consumer<Object> lambdaField = value -> {
    consume((1 + 2)); // violation 'around expression'
    Object assigned = consume((1 + 2));
    // violation above 'around assignment right-hand side'
};

Object consume(Object value) {
    return value;
}
