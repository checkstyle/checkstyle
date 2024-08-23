/*
UnusedLambdaParameterShouldBeUnnamed

*/


//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlambdaparametershouldbeunnamed;

import java.util.function.BiFunction;

public class InputUnusedLambdaParameterShouldBeUnnamedMultipleParameters {

    String x, y;

    void testUnused() {

        BiFunction<String, String, String> function = (x, y) -> {
            // 2 violations above:
            //                  'Unused lambda parameter 'x' should be unnamed'
            //                  'Unused lambda parameter 'y' should be unnamed'
            return "x" + "y";
        };

        // violation below, 'Unused lambda parameter 'y' should be unnamed'
        function = (x, y) -> x + "y";

        // violation below, 'Unused lambda parameter 'x' should be unnamed'
        function = (x, y) -> {
            return this.x + y;
        };

        function = (x, y) -> this.x + this.y;
        // 2 violations above:
        //                  'Unused lambda parameter 'x' should be unnamed'
        //                  'Unused lambda parameter 'y' should be unnamed'

        // violation below, 'Unused lambda parameter 'X' should be unnamed'
        function = (X, Y) -> {
            X x = new X();
            return x.s() + Y;
        };


        function = (x, y) -> {
            // 2 violations above:
            //                  'Unused lambda parameter 'x' should be unnamed'
            //                  'Unused lambda parameter 'y' should be unnamed'
            return x("x") + y("y");
        };

        // violation below, 'Unused lambda parameter 'X' should be unnamed'
        function = (X, Y) -> {
            Object a = new X();
            return a.toString() + Y.toString();
        };

        function = (X, Y) -> {
            x = X;
            return x + Y.toString();
        };

    }

    void testAllUsed() {
        BiFunction<String, String, String> function = (x, y) -> {
            return x + y;
        };

        function = (x, y) -> x + y;

        function = (x, y) -> {
            return this.x + y + x + this.y;
        };
    }

    void testWithUnnamed() {
        BiFunction<String, String, String> function = (_, _) -> {
            return "x" + "y";
        };

        // violation below, 'Unused lambda parameter 'x' should be unnamed'
        function = (x, _) -> "x" + "y";

        function = (_, y) -> {
            return this.x + this.y + y;
        };

        // violation below, 'Unused lambda parameter 'y' should be unnamed'
        function = (_, y) -> {
            return this.x + this.y;
        };

        function = (_, _) -> {
            return this.x + this.y;
        };

        function = (_, _) -> {
            X x = new X();
            return x.s() + "x";
        };
    }

    class X {
        String s() {
            return "x";
        }
    }

    String x(String x) {
        return x;
    }

    String y(String y) {
        return y;
    }
}
