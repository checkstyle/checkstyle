/*
LambdaParameterName
format = (default)^([a-z][a-zA-Z0-9]*|_)$


*/

// non-compiled with javac: Compilable with Java25

import java.util.function.Function;

Function<String, String> valid = s -> s;

// violation 2 lines below 'Name 'S' must match pattern'
Function<String, String> invalid =
    S -> S;

void main() {
}
