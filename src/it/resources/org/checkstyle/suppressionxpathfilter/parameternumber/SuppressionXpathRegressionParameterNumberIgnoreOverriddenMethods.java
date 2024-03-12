package org.checkstyle.suppressionxpathfilter.parameternumber;

public class SuppressionXpathRegressionParameterNumberIgnoreOverriddenMethods
        extends SuppressionXpathRegressionParameterNumberDefault {

     public SuppressionXpathRegressionParameterNumberIgnoreOverriddenMethods(int a, // warn
                    int b, int c, int d, int e, int f, int g, int h)
    {
    }
    @Override
    void myMethod(int a, int b, int c, int d, int e, int f, int g, int h, // ok
                    int k, int l, int m) {
    }
}
