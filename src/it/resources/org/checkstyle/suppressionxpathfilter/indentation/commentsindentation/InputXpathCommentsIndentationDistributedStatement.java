package org.checkstyle.suppressionxpathfilter.indentation.commentsindentation;

import java.util.Arrays;

public class InputXpathCommentsIndentationDistributedStatement {
    public void foo() {
        String s = "";
        int n = s
                    .length();
                        // Comment // warn
    }
}
