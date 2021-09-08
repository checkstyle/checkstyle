package org.checkstyle.suppressionxpathfilter.adjacentannotation.annotationonsameline;

public class SuppressionXpathRegressionAdjacentAnnotationOne {
    @Deprecated int x;

    @Deprecated //warn
    public int getX() {
        return (int) x;
    }
}

