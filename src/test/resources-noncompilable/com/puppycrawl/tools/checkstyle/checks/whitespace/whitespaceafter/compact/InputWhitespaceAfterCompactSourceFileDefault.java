/*
WhitespaceAfter
tokens = (default)COMMA, SEMI, TYPECAST, LITERAL_IF, LITERAL_ELSE, LITERAL_WHILE, \
         LITERAL_DO, LITERAL_FOR, LITERAL_FINALLY, LITERAL_RETURN, LITERAL_YIELD, \
         LITERAL_CATCH, DO_WHILE, ELLIPSIS, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, \
         LITERAL_TRY, LITERAL_CASE, LAMBDA, LITERAL_WHEN


*/

// non-compiled with javac: Compilable with Java25

int add(int first, int second) {
    return first + second;
}

void main() {
    int total = add(1,2); // violation "',' is not followed by whitespace."
    int[] numbers = {1, 2,3}; // violation "',' is not followed by whitespace."
    if(total > 0) { // violation "'if' is not followed by whitespace."
        System.out.println(total);
    }
    System.out.println(numbers.length);
}
