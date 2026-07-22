/*
ExpressionOverBlockLambda


*/

// non-compiled with javac: Compilable with Java25

void main() {
    // violation, 'Expression lambdas are preferred over single-line block lambdas.'
    Runnable a = () -> { System.out.println("hello"); };
}
