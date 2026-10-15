/*
VariableDeclarationUsageDistance
allowedDistance = (default)3
ignoreVariablePattern = (default)
validateBetweenScopes = (default)false
ignoreFinal = (default)true


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int a = 1;
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println(a);
}

void methodWithViolation() {
    int b = 1; // violation 'Distance .* is 4.'
    first();
    second();
    third();
    System.out.println(b);
}

void first() {
}

void second() {
}

void third() {
}
