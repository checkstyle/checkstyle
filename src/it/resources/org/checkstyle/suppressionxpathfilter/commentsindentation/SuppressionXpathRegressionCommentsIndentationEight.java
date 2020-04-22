package org.checkstyle.suppressionxpathfilter.commentsindentation;

public class SuppressionXpathRegressionCommentsIndentationEight {
    public void foo() {
        String s = "F"
        // Comment // warn
            + "O"
            + "O";
    }
}
