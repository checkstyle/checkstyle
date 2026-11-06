/*
LambdaParameterName
format = (default)^([a-z][a-zA-Z0-9]*|_)$


*/

// non-compiled with javac: Compilable with Java25

java.util.function.Function<String, String> goodNamed = (value) -> value;

java.util.function.Function<String, String> badNamed =
        (Bad) -> Bad; // violation 'Name 'Bad' must match pattern'

java.util.function.BiFunction<String, String, String> twoParams =
        (first, BAD_NAME) -> first; // violation 'Name 'BAD_NAME' must match pattern'
