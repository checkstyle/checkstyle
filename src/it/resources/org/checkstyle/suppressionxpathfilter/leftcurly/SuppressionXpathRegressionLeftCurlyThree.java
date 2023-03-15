package org.checkstyle.suppressionxpathfilter.leftcurly;

public class SuppressionXpathRegressionLeftCurlyThree {
    public void sample(boolean flag) {
        if (flag) { String.CASE_INSENSITIVE_ORDER.equals("it is ok."); } //warn
    }
}
