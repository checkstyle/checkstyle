/*
LambdaParameterName
format = (default)^([a-z][a-zA-Z0-9]*|_)$


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.naming.lambdaparametername;

import java.util.stream.Stream;

public class InputLambdaParameterNameSwitchExpression {
    boolean method1(Nums k, String string) {
        switch (k) {
            case ONE:
                Stream.of(string.split(" "))
                        .map(word -> word.trim())
                        .anyMatch(Word -> "in".equals(Word)); // violation, 'Name 'Word' must match'
                break;
            default:
        }
        return false;
    }

    boolean method2(Nums k, String string) {
        switch (k) {
            case ONE -> {
                Stream.of(string.split(" "))
                        .map(word -> word.trim())
                        .anyMatch(Word -> "in".equals(Word)); // violation, 'Name 'Word' must match'
                System.out.println("case one");
            }
            default -> Stream.of(string.split(" "))
                    .map(word -> word.trim())
                    .anyMatch(Word -> "in".equals(Word)); // violation, 'Name 'Word' must match'
        }
        return true;
    }

    boolean method3(Nums k, String string) {
        return switch (k) {
            case ONE:
                yield Stream.of(string.split(" "))
                        .map(word -> word.trim())
                        .anyMatch(Word -> "in".equals(Word)); // violation, 'Name 'Word' must match'
            default:
                yield false;
        };
    }

    boolean method4(Nums k, String string) {
        return switch (k) {
            case ONE -> {
                yield Stream.of(string.split(" "))
                        .map(word -> word.trim())
                        .anyMatch(Word -> "in".equals(Word)); // violation, 'Name 'Word' must match'
            }
            default -> { yield false; }
        };
    }


    enum Nums {ONE, TWO, THREE}
}
