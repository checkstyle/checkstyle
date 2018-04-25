package org.checkstyle.suppressionxpathfilter.explicitinitialization;

public class SuppressionXpathRegressionExplicitTwo {
    private int a;

    private Object bar = null; //warn
}
