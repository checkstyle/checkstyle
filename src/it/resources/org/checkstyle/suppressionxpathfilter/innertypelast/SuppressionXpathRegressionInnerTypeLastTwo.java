package org.checkstyle.suppressionxpathfilter.innertypelast;

public class SuppressionXpathRegressionInnerTypeLastTwo {

    static {}; // OK
    class Inner {};
    public void innerMethod() {} //warn

}
