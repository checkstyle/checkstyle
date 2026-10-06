/*
OpenjdkLineWrapping


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.openjdklinewrapping;

class InputOpenjdkLineWrappingOperators {
    void operators(int first, int second, boolean condition) {
        int leading = first
                + second;
        int trailing = first +
                second;
        int wrongLeading = first
            + second; // violation 'incorrect indentation level 12'
        int wrongTrailing = first +
            second; // violation 'incorrect indentation level 12'
        first +=
                second;
        first +=
            second; // violation 'incorrect indentation level 12'
        int unary = -
                first;
        int wrongUnary = -
            first; // violation 'incorrect indentation level 12'
        int cast = (int)
                first;
        int wrongCast = (int)
            first; // violation 'incorrect indentation level 12'
        int parenthesized = (
                first + second);
        int wrongParenthesized = (
            first + second); // violation 'incorrect indentation level 12'
        int[] array = new int[
                first];
        int[] wrongArray = new int[
            first]; // violation 'incorrect indentation level 12'
        int index = array[
                first];
        int wrongIndex = array[
            first]; // violation 'incorrect indentation level 12'
        boolean instance = array instanceof
                Object;
        boolean wrongInstance = array instanceof
            Object; // violation 'incorrect indentation level 12'
        int ternary = condition ?
                first :
                second;
        int wrongTernary = condition ?
            first : second; // violation 'incorrect indentation level 12'
        java.util.function.IntSupplier supplier = () ->
                second;
        java.util.function.IntSupplier wrongSupplier = () ->
            second; // violation 'incorrect indentation level 12'
        Runnable body = () ->
        {
            int nested = second
                    + second;
        };
        int result = switch (first) {
            default -> second
                    + 1;
        };
        consume(first
                ++,
                        second);
        int[][] partial = new int[1][];
        int[][] initialized = new int[][] {{1}};
        java.util.function.IntUnaryOperator negate = number -> -number;
        ++first;
        first--;
        first = +second;
        first <<= 1;
        first |= second;
        first ^= second;
    }

    void consume(int... values) {
    }
}
