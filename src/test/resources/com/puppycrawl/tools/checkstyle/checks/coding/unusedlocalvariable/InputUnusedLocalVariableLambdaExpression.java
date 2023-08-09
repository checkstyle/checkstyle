/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.util.function.Supplier;

public class InputUnusedLocalVariableLambdaExpression {
    private final LambdaTest<String> myComponent = LambdaTest.lazy(() -> {
        String foo = ""; // ok
        String hoo = ""; // violation
        new Runnable() {
            String hoo = "";

            @Override
            public void run() {
                String j = hoo; // violation
                String ja = foo; // ok
                ja += "asd";
            }
        };
        return "";
    });

    final LambdaTest<String> nestedLambdas = LambdaTest.lazy(() -> {
        String foo = ""; // ok
        String hoo = ""; // ok
        String hoo2 = ""; // violation
        String hoo3 = ""; // violation
        final LambdaTest<String> myComponent = LambdaTest.lazy(() -> { // violation
            final LambdaTest<String> myComponent3 = LambdaTest.lazy(() -> { // violation
                new Runnable() {
                    String hoo2 = "";

                    @Override
                    public void run() {
                        String j = hoo; // violation
                        String ja = foo; // ok
                        ja += hoo2;
                    }
                };
                return "";
            });
            new Runnable() {
                String hoo3 = "";

                @Override
                public void run() {
                    String j = hoo3; // violation
                    String ja = foo; // ok
                    ja += "asd";
                }
            };
            return "";
        });
        new Runnable() {
            String hoo2 = "";

            @Override
            public void run() {
                String j = hoo2; // ok
                String ja = foo; // violation
                j += hoo2;
            }
        };
        return "";
    });

    final LambdaTest<String> nestedLambdas2 = LambdaTest.lazy(() -> {
        String hoohaa = ""; // violation
        final LambdaTest<String> nestedLambdas = LambdaTest.lazy(() -> {
            new LambdaTest<String>() {
                void foo() {
                    String j = hoohaa; // ok
                    j += "asd";
                }
            };
            return "";
        });
        return nestedLambdas.toString();
    });
}

class LambdaTest<T> {
    String hoohaa = "";
    public static <T> LambdaTest<T> lazy(Supplier<T> supplier) {
        return new LambdaTest<>();
    }
}
