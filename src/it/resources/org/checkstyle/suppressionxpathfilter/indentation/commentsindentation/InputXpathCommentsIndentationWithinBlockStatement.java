package org.checkstyle.suppressionxpathfilter.indentation.commentsindentation;

public class InputXpathCommentsIndentationWithinBlockStatement {
    public void foo() {
        String s = "F"
        // Comment // warn
            + "O"
            + "O";
    }
}
