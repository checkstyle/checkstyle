package org.checkstyle.suppressionxpathfilter.coding.emptystatement;

public class InputXpathEmptyStatementWhile {
    public void foo() {
        while (true); // warn
    }
}
