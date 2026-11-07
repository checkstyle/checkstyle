/*
UnnecessaryParentheses
tokens = EXPR, ASSIGN


*/

// non-compiled with javac: Compilable with Java25
void main() {
    Object value = consume((1 + 2)); // violation 'around assignment right-hand side'
    consume((1 + 2)); // violation 'around expression'
}

Object consume(Object value) {
    return value;
}
