/*
ParameterAssignment


*/


package com.puppycrawl.tools.checkstyle.checks.coding.parameterassignment;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;

public class InputParameterAssignmentLambdaInSwitchRule {

    Consumer<Integer> blockBody(int k) {
        return switch (k) {
            case 1 -> x -> {
                x = 2; // violation 'Assignment of parameter 'x' is not allowed.'
            };
            default -> null;
        };
    }

    IntUnaryOperator expressionBody(int k) {
        return switch (k) {
            case 1 -> x -> ++x; // violation 'Assignment of parameter 'x' is not allowed.'
            default -> x -> x;
        };
    }

    BiFunction<Integer, Integer, Integer> multipleParameters(int k) {
        return switch (k) {
            case 1 -> (x, y) -> y += x; // violation 'Assignment of parameter 'y' is not allowed.'
            default -> (x, y) -> x + y;
        };
    }

    Consumer<Integer> typedParameter(int k) {
        return switch (k) {
            case 1 -> (Integer x) -> {
                x++; // violation 'Assignment of parameter 'x' is not allowed.'
            };
            default -> (Integer x) -> { };
        };
    }

    Function<Integer, IntUnaryOperator> nestedLambda(int k) {
        return switch (k) {
            case 1 -> x -> y -> {
                y = x; // violation 'Assignment of parameter 'y' is not allowed.'
                return y;
            };
            default -> x -> y -> x + y;
        };
    }

    Consumer<Integer> localVariableIsNotParameter(int k) {
        return switch (k) {
            case 1 -> x -> {
                int y;
                y = x;
            };
            default -> null;
        };
    }

    int scopeIsRestoredAfterSwitch(int a) {
        final Consumer<Integer> action = switch (a) {
            case 1 -> x -> { };
            default -> null;
        };
        int x;
        x = a;
        a = x; // violation 'Assignment of parameter 'a' is not allowed.'
        return a;
    }

    Consumer<Integer> lambdaInsideRuleBlock(int k) {
        return switch (k) {
            case 1 -> {
                yield x -> {
                    x = 2; // violation 'Assignment of parameter 'x' is not allowed.'
                };
            }
            default -> null;
        };
    }

    Consumer<Integer> lambdaInOldStyleSwitch(int k) {
        switch (k) {
            case 1:
                return x -> {
                    x = 2; // violation 'Assignment of parameter 'x' is not allowed.'
                };
            default:
                return null;
        }
    }
}
