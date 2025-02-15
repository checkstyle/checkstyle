/*
NeedBraces
allowSingleLineStatement = true
allowEmptyLoopBody = (default)false
tokens = LITERAL_CASE, LITERAL_DEFAULT, LAMBDA


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

import java.util.Arrays;
import java.util.List;

public class InputNeedBracesSwitchExpressionAndLambdaAllowSingleLine {

    public void testLambda(String x, String y, String z) {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        numbers = numbers.stream()
                .map(i -> 0).toList();

        numbers = numbers.stream()
                .map(i -> x.length()).toList();

        numbers = numbers.stream()
                .map(i -> x.length() + y.length() // violation ''->' construct must use '{}'s'
                        + z.length()
                ).toList();

        numbers = numbers.stream()
                .map(i -> {
                    return x.length() + y.length()
                            + z.length();
                }).toList();
    }

    public void testSwitchExpression(int x, String s, Object o) {
        int a = switch (x) {
            case 1 -> 1;
            default -> 0;
        };

        int b;
        b = switch (x) {
            case 1 -> 1 + 2 // violation ''case' construct must use '{}'s'
                    + 3 + 4 * 5;
            default -> throw new IllegalStateException();
        };

        int c = switch (o) {
            case Integer i -> i;
            // violation below ''case' construct must use '{}'s'
            case String str when x == 1 -> str.length()
                    + str.length() * 5 + str.lastIndexOf("a");
            case String str -> str.length();
            default -> 0;
        };

        int bb;
        bb = switch (x) {
            case 1 -> {
                yield 1 + 2
                        + 3 + 4 * 5;
            }
            default -> throw new IllegalStateException();
        };

        int cc = switch (o) {
            case Integer i -> i;
            case String str when x == 1 -> {
                yield str.length()
                        + str.length() * 5 + str.lastIndexOf("a");
            }
            case String str -> str.length();
            default -> 0;
        };
    }
}
