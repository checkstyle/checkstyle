package org.checkstyle.suppressionxpathfilter.commentsindentation;

import java.util.Arrays;

public class SuppressionXpathRegressionCommentsIndentationFour {
    public void foo() {
        String s = "";
        int n = s
                    .length();
                        // Comment // warn
    }
}
