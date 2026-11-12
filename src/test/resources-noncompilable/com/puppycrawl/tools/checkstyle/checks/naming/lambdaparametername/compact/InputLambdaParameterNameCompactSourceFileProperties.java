/*
LambdaParameterName
format = ^[A-Z][a-zA-Z0-9]*$


*/

// non-compiled with javac: Compilable with Java25

java.util.function.Function<String, String> goodNamed = (Value) -> Value;

java.util.function.Function<String, String> badNamed =
        (lower) -> lower; // violation 'Name 'lower' must match pattern'

void main() {
}
