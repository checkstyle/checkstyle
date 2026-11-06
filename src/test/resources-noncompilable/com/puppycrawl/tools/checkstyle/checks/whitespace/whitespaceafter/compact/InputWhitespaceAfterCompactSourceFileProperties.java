/*
WhitespaceAfter
tokens = COMMA, SEMI


*/

// non-compiled with javac: Compilable with Java25

int add(int first, int second) {
    return first + second;
}

void main() {
    int total = add(1,2); // violation "',' is not followed by whitespace."
    int copy = total;int again = copy; // violation "';' is not followed by whitespace."
    if(total > 0) {
        System.out.println(total);
    }
    System.out.println(again);
}
