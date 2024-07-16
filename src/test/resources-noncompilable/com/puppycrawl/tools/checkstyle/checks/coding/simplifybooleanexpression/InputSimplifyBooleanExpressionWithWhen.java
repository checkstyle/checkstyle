/*
SimplifyBooleanExpression


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.simplifybooleanexpression;

public class InputSimplifyBooleanExpressionWithWhen {

    void test(Object o) {
        if (o instanceof Boolean b && b == true) { } // violation

        switch (o) {
            case R(boolean x, _) when x == true -> {} // violation
            case R(_, boolean y) when y != false -> {} // violation
            default -> {}
        }

        switch (o) {
            case R(boolean x, _) when x == false -> {} // violation
            case R(_, boolean y) when (!(y != true)) -> {} // violation
            default -> {}
        }
    }

    void test2(Object o) {

        if (o instanceof Boolean b && b) { }

        switch (o) {
            case R(boolean x, _) when x -> {}
            case R(_, boolean y) when y -> {}
            default -> {}
        }

        switch (o) {
            case R(boolean x, _) when !x -> {}
            case R(_, boolean y) when y -> {}
            default -> {}
        }

    }
    record R(boolean x, boolean y) {}
}
