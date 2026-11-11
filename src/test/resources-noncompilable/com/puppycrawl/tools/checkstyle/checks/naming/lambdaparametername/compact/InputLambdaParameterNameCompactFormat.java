/*
LambdaParameterName
format = ^[A-Z][a-zA-Z0-9]*$


*/

// non-compiled with javac: Compilable with Java25

import java.util.function.Function;

Function<String, String> valid = S -> S;

// violation 2 lines below 'Name 's' must match pattern'
Function<String, String> invalid =
    s -> s;

void main() {
}
