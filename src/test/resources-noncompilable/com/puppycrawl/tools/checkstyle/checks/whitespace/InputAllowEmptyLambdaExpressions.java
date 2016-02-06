//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.function.*;

public class InputAllowEmptyLambdaExpressions {
    Runnable noop = () -> {};
    Runnable noop2 = () -> {
        int x = 10;
    };
    BinaryOperator<Integer> sum = (x, y) -> x + y;
    Runnable noop3 = () -> {;};
    Runnable noop4 = () -> {new String();};
}
