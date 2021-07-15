package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionSimple {

    int w, a;

    public static void main(String... args) {
        int[][] z = {{1}};
        String s = "Hello world!";
    }

    private void method(String x, Integer y, String... arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
