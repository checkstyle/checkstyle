package org.checkstyle.checks.suppressionxpathfilter.commentsindentation;

public class InputXpathCommentsIndentationNonEmptyCase {
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
