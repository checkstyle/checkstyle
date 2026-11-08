/*
UnusedLambdaParameterShouldBeUnnamed


*/

// non-compiled with javac: Compilable with Java25

import java.util.function.BiFunction;
import java.util.function.Function;

int offset = 1;

void main() {
}

Function<Integer, Integer> constant = value -> 42; // violation 'Unused lambda .* 'value''

BiFunction<Integer, Integer, Integer> sum = (left, right) -> left + right;

Function<Integer, Integer> unnamed = _ -> 42;

int apply(int input) {
    Function<Integer, Integer> shift = offset -> input + 1; // violation 'Unused .* 'offset''
    Function<Integer, Integer> twice = number -> number * 2;
    return shift.apply(input) + twice.apply(input);
}
