package org.checkstyle.suppressionxpathfilter.emptystatement;

public class InputXpathEmptyStatementLoops {
    public void foo() {
        for (int i = 0; i < 5; i++); // warn
    }
}
