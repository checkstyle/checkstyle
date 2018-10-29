package org.checkstyle.suppressionxpathfilter.rightcurly;

public class SuppressionXpathRegressionRightCurlyOne {
    public void test(int x) {
        if (x > 0)
        {
            return;
        } //warn
        else if (x < 0) {
            ;
        }
    }
}
