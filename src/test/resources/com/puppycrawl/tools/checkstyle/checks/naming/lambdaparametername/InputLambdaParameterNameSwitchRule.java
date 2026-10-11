/*
LambdaParameterName
format = (default)^([a-z][a-zA-Z0-9]*|_)$


*/

package com.puppycrawl.tools.checkstyle.checks.naming.lambdaparametername;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class InputLambdaParameterNameSwitchRule {
    Function<String, String> singleParameter(int value) {
        return switch (value) {
            case 0 -> Word -> Word.trim(); // violation 'Name 'Word' must match'
            case 1 -> (Word) -> Word.trim(); // violation 'Name 'Word' must match'
            case 2 -> (String Word) -> Word.trim(); // violation 'Name 'Word' must match'
            case 3 -> word -> word.trim();
            case 4 -> Word -> { // violation 'Name 'Word' must match'
                return Word.trim();
            };
            default -> word -> {
                return word.trim();
            };
        };
    }

    BiFunction<String, String, String> multipleParameters(int value) {
        return switch (value) {
            case 0 -> (First, second) -> First + second; // violation 'Name 'First' must match'
            default -> (first, second) -> first + second;
        };
    }

    Supplier<String> noParameters(int value) {
        return switch (value) {
            case 0 -> () -> "zero";
            default -> () -> "other";
        };
    }

    Function<String, Function<String, String>> nested(int value) {
        return switch (value) {
            case 0 -> Outer -> inner -> Outer + inner; // violation 'Name 'Outer' must match'
            default -> outer -> Inner -> outer + Inner; // violation 'Name 'Inner' must match'
        };
    }
}
