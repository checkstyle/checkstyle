package org.checkstyle.suppressionxpathfilter.emptystatement;

public class InputXpathEmptyStatement1 {
    public void foo() {
        for (int i = 0; i < 5; i++); // warn
    }
}
