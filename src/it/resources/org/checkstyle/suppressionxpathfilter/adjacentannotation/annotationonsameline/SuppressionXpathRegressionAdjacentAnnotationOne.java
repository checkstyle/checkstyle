package org.checkstyle.suppressionxpathfilter.annotationonsameline;

public class SuppressionXpathRegressionAnnotationOnSameLineOne {
    @Deprecated int x;

    @Deprecated //warn
    public int getX() {
        return (int) x;
    }
}

