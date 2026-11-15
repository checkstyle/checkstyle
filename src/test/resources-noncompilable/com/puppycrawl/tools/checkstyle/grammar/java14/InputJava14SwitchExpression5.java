// non-compiled with eclipse: local variable x may not have been initialized
// non-compiled with javac: Compilable with Java21 individually
package com.puppycrawl.tools.checkstyle.grammar.java14;

import java.util.function.Supplier;

public class InputJava14SwitchExpression5 {

    enum T {
        A, B, C;
    }

    private String print2(int i, int j, int k) {
        return switch (i) {
            case 0:
                String r;
                OUTER: switch (j) {
                    case 0:
                        String res;
                        INNER: switch (k) {
                            case 0: res = "0-0-0"; break;
                            case 1: res = "0-0-1"; break;
                            case 2: res = "0-0-2"; break INNER;
                            default: r = "0-0-X"; break OUTER;
                        }
                        r = res;
                        break;
                    case 1:
                        r = "0-1";
                        break;
                    default:
                        r = "0-X";
                        break;
                }
                yield r;
            case 1:
                yield "1";
            case 2:
                LOP: while (j-- > 0) {
                    if (k == 5) {
                        k--;
                        continue;
                    }
                    break LOP;
                }
                Supplier<String> getter = () -> { return "2-X-5"; };
                yield getter.get();
            default:
                yield "X";
        };
    }

    private String expression1(T t) {
        String help = "";
        return switch (t) {
            case A: help = "a";
            case B: help += "b";
            default: yield help;
        };
    }

    private String expression2(T t) {
        String help = "";
        return switch (t) {
            case A: help = "a";
            case B: help += "b";
            default: yield help;
        };
    }
}
