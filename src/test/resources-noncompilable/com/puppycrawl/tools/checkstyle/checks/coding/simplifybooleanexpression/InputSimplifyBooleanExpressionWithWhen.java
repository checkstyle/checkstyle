/*
SimplifyBooleanExpression


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.simplifybooleanexpression;

public class InputSimplifyBooleanExpressionWithWhen {

    void test(Object o) {
        if (o instanceof Boolean b && b == true) { } // violation '"b == true" can be simpified to "b"'

        switch (o) {
            case R(boolean x, _) when x == true -> {} // violation '"x == true" can be simplified to "x"'
            case R(_, boolean y) when y != false -> {} // violation '"y != false" can be simplified to "y"'
            default -> {}
        }

        switch (o) {
            case R(boolean x, _) when x == false -> {} // violation '"x == false" can be simplified to "!x"'
            case R(_, boolean y) when (!(y != true)) -> {} // violation '"!(y != true)" can be simplified to "y"'
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
