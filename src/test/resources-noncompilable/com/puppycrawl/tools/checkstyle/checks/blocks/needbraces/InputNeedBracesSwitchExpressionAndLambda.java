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
                .map(i -> 0).toList(); // violation ''->' construct must use '{}'s'

        numbers = numbers.stream()
                .map(i -> x.length()).toList(); // violation ''->' construct must use '{}'s'

        numbers = numbers.stream()
                .map(i -> x.length() + y.length() // violation ''->' construct must use '{}'s'
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
            case 1 -> 1; // violation ''case' construct must use '{}'s'
            default -> 0; // violation ''default' construct must use '{}'s'
        };

        int b;
        b = switch (x) {
            case 1 -> 1 + 2 // violation ''case' construct must use '{}'s'
                    + 3 + 4 * 5;
            // violation below ''default' construct must use '{}'s'
            default -> throw new IllegalStateException();
        };

        int c = switch (o) {
            case Integer i -> i; // violation ''case' construct must use '{}'s'
            // violation below ''case' construct must use '{}'s'
            case String str when x == 1 -> str.length()
                    + str.length() * 5 + str.lastIndexOf("a");
            case String str -> str.length(); // violation ''case' construct must use '{}'s'
            default -> 0; // violation ''default' construct must use '{}'s'
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
