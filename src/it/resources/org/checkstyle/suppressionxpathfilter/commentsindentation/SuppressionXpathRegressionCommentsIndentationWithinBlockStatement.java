package org.checkstyle.suppressionxpathfilter.commentsindentation;

public class SuppressionXpathRegressionCommentsIndentationWithinBlockStatement {
    public void foo() {
        String s = "F"
        // Comment // warn
            + "O"
            + "O";
    }
}
