package org.checkstyle.suppressionxpathfilter.parameternumber;

public class SuppressionXpathRegressionParameterNumberDefault {

    void myMethod(int a, int b, int c, int d, int e, int f, int g, int h, // warn
                  int i, int j, int k) {
    }

    public SuppressionXpathRegressionParameterNumberDefault() { // ok
    }

    void myMethod2(int a, int b, int c, int d) { // ok
    }

}
