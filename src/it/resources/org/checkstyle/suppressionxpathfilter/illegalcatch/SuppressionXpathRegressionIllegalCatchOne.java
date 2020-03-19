package org.checkstyle.suppressionxpathfilter.illegalcatch;

public class SuppressionXpathRegressionIllegalCatchOne {
    public void foo() {
        try {
        } catch (RuntimeException e) { // warn
        }
    }
}
