package org.checkstyle.checks.suppressionxpathfilter.commentsindentation;

public class InputXpathCommentsIndentationWithinBlockStatement {
    public void foo() {
        String s = "F"
        // Comment // warn
            + "O"
            + "O";
    }
}
