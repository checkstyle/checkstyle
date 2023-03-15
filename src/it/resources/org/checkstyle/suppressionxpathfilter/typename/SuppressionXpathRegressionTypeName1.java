package org.checkstyle.suppressionxpathfilter.typename;

public class SuppressionXpathRegressionTypeName1 {
    public interface FirstName {} // OK
    private class SecondName_ {} // warn

}
