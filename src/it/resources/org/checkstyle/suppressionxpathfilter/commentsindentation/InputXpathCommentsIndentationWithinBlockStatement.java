package org.checkstyle.suppressionxpathfilter.commentsindentation;

public class InputXpathCommentsIndentationWithinBlockStatement {
    public void foo() {
        String s = "F"
        // Comment // warn
            + "O"
            + "O";
    }
}
