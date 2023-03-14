package org.checkstyle.suppressionxpathfilter.emptystatement;

public class SuppressionXpathRegressionEmptyStatement2 {
    public void foo() {
        int i = 0;
        if (i > 3); // warn
    }
}
