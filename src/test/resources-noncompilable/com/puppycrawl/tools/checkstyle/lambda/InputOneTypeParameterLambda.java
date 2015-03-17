//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.lambda;
import java.util.List;
import java.util.Arrays;

public class InputOneGenericParameterLambda {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        int sum = list.stream().map((Integer x) -> x*x);
    }
}