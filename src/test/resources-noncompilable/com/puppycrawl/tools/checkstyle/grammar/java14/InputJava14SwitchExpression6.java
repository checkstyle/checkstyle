// non-compiled with eclipse: local variable x may not have been initialized
// non-compiled with javac: Compilable with Java21 individually
package com.puppycrawl.tools.checkstyle.grammar.java14;

public class InputJava14SwitchExpression6 {

    I<String> lambdaCapture1(int i) {
        int j = i + 1;
        I<String> r = switch (i) {
            case 0 -> () -> "0" + i; //capture parameter
            case 1 -> () -> "1" + j; //capture local variable
            default -> {
                String k = "D";
                yield () -> k; //capture local from the switch expr.
            }
        };

        return r;
    }

    I<String> lambdaCapture2(int i) {
        int j = i + 1;

        return switch (i) {
            case 0 -> () -> "0" + i; //capture parameter
            case 1 -> () -> "1" + j; //capture local variable
            default -> {
                String k = "D";
                yield () -> k; //capture local from the switch expr.
            }
        };
    }

    interface I<T> {
        public T t();
    }

    static {
        int i = 0;
        int dummy = 1 + switch (i) {
            case -1: yield 1;
            default:
                i++;
                yield 1;
        };
        if (i != 1) {
            throw new IllegalStateException("Side effects missing.");
        }
    }

    private static int getValueViaYield(String mode) {
        int yield = 0;
        return switch (mode) {
            case "a", "b":
                int x = yield + 2;
                yield x;
            default:
                yield - 1;
        };
    }

    private static int getValueViaYield2(String mode) {
        return switch (mode) {
            case "a", "b":
                int yield = 42;
                yield yield;
            default:
                yield - 1;
        };
    }
}
