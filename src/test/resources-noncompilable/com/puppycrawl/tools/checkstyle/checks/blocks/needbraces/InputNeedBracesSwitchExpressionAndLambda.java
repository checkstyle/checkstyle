/*
NeedBraces
allowSingleLineStatement = false
allowEmptyLoopBody = (default)false
tokens = LITERAL_CASE, LITERAL_DEFAULT, LAMBDA


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

import java.util.Arrays;
import java.util.List;

public class InputNeedBracesSwitchExpressionAndLambda {

    public void testLambda(String x, String y, String z) {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        numbers = numbers.stream()
                .map(i -> 0).toList(); // violation

        numbers = numbers.stream()
                .map(i -> x.length()).toList(); // violation

        numbers = numbers.stream()
                .map(i -> x.length() + y.length() // violation
                        + z.length()
                ).toList();

        numbers = numbers.stream()
                .map(i -> {
                    return 0;
                }).toList();

        numbers = numbers.stream()
                .map(i -> {
                    return x.length();
                }).toList();

        numbers = numbers.stream()
                .map(i -> {
                    return x.length() + y.length()
                            + z.length();
                }).toList();
    }

    public void testSwitchExpression(int x, String s, Object o) {
        int a = switch (x) {
            case 1 -> 1; // violation
            default -> 0; // violation
        };

        int b;
        b = switch (x) {
            case 1 -> 1 + 2 // violation
                    + 3 + 4 * 5;
            default -> throw new IllegalStateException(); // violation
        };

        int c = switch (o) {
            case Integer i -> i; // violation
            case String str when x == 1 -> str.length() // violation
                    + str.length() * 5 + str.lastIndexOf("a");
            case String str -> str.length(); // violation
            default -> 0; // violation
        };

        int aa = switch (x) {
            case 1 -> {
                yield 1;
            }
            default -> {
                yield 0;
            }
        };

        int bb;
        bb = switch (x) {
            case 1 -> {
                yield 1 + 2
                        + 3 + 4 * 5;
            }
            default -> {
                throw new IllegalStateException();
            }
        };

        int cc = switch (o) {
            case Integer i -> {
                yield i;
            }
            case String str when x == 1 -> {
                yield str.length()
                        + str.length() * 5 + str.lastIndexOf("a");
            }
            case String str -> {
                yield str.length();
            }
            default -> {
                yield 0;
            }
        };
    }
}
