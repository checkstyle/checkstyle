package org.checkstyle.checks.suppressionxpathfilter.commentsindentation;

public class InputXpathCommentsIndentationEmptyCase {
    int n;

    public void foo() {
        switch(n) {
            case 1:
// Comment // warn
            default:
        }
    }
}
