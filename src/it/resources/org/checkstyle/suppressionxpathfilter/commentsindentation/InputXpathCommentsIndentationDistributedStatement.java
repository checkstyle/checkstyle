package org.checkstyle.suppressionxpathfilter.commentsindentation;

import java.util.Arrays;

public class InputXpathCommentsIndentationDistributedStatement {
    public void foo() {
        String s = "";
        int n = s
                    .length();
                        // Comment // warn
    }
}
