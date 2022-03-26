package org.checkstyle.suppressionxpathfilter.methodname;

public class SuppressionXpathRegressionMethodName3 {
    public void FirstMethod() {} // OK
    protected void SecondMethod() {} // OK
    private void ThirdMethod() {} // warn
}
