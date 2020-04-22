package org.checkstyle.suppressionxpathfilter.commentsindentation;

public class SuppressionXpathRegressionCommentsIndentationSeven {
    int n;

    public void foo() {
        switch(n) {
            case 1:
// Comment // warn
            default:
        }
    }
}
