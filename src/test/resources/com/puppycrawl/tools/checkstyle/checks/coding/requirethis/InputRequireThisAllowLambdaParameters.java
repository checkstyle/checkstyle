package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.util.function.Consumer;
public class InputRequireThisAllowLambdaParameters {
    private String s1 = "foo1";
    int x=-1;
    int y=-2;

    void foo1() {
        final java.util.List<String> strings = new java.util.ArrayList<>();
        strings.add("foo1");
        strings.stream().filter(s1 -> s1.contains("f"))  // NO violation; s1 is a lambda parameter
                .collect(java.util.stream.Collectors.toList());

        s1 = "foo1"; // violation; validateOnlyOverlapping=false
    }

    void foo2() {
        final java.util.List<String> strings = new java.util.ArrayList<>();
        strings.add("foo1");
        strings.stream().filter(s1 -> s1.contains(s1 = s1 + "2"))// NO violation;s1 is lambda param
                .collect(java.util.stream.Collectors.toList());
    }

    class FirstLevel {

        int x;
        int y;
        int z;
        void methodInFirstLevel(int x) {
            Consumer<Integer> myConsumer = (y) ->   // NO violation; y is a lambda parameter
            {
                new String("x = " + x);
                new String("y = " + y);  // NO violation; y is a lambda parameter
                new String("InputRequireThisAllowLambdaParameters.this.x = " +
                        InputRequireThisAllowLambdaParameters.this.x);
                y=x+z++; // 1 violation for z; NO violation for y; y is a lambda parameter
            };
            myConsumer.accept(x);
        }
    }
}

class Calculator {

    int a;
    int b;
    interface IntegerMath {
        int operation(int a, int b);
    }

    public int operateBinary(int a, int b, IntegerMath op) {
        return op.operation(a, b);
    }

    public void addSub(String... args) {

        Calculator myApp = new Calculator();
        IntegerMath addition = (a, b) -> a = a + b;  // NO violations
        IntegerMath subtraction = (a, b) -> a = a - b; // NO violations
        myApp.operateBinary(20, 10, subtraction);
        myApp.operateBinary(a++, b, addition);  // 2 violations
    }
}
