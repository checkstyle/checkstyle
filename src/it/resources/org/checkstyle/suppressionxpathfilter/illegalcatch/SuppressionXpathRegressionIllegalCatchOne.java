package org.checkstyle.suppressionxpathfilter.illegalcatch;

public class SuppressionXpathRegressionIllegalCatchOne {
    public void fun() {
        try {
        } catch (RuntimeException e) { // warn
        }
    }
}
