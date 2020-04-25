package org.checkstyle.suppressionxpathfilter.commentsindentation;

public class SuppressionXpathRegressionCommentsIndentationSix {
    int n;

    public void foo() {
        switch(n) {
            case 1:
                if (true) {}
                   // Comment // warn
            default:
        }
    }
}
