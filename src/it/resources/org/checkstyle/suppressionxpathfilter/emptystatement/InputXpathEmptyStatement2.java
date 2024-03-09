package org.checkstyle.suppressionxpathfilter.emptystatement;

public class InputXpathEmptyStatement2 {
    public void foo() {
        int i = 0;
        if (i > 3); // warn
    }
}
