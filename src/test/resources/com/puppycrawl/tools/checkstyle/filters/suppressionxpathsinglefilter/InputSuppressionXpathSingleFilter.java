package com.puppycrawl.tools.checkstyle.filters.suppressionxpathsinglefilter;

public class InputSuppressionXpathSingleFilter {
    private int countTokens() {
        double pi = 3.14;
        return 123;
    }

    public String getName() {
        int someVariable = 123;
        return "InputSuppressByXpathThree";
    }

    public int sum(int a, int b) {
        String someVariable = "Hello World";
        return a + b;
    }

}
