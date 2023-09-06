package org.checkstyle.suppressionxpathfilter.illegalcatch;

public class SuppressionXpathRegressionIllegalCatchTwo {
    public void methodOne() {
        try {
            foo();
        } catch (java.sql.SQLException e) {
        }
    }

    private void foo() throws java.sql.SQLException {
    }

    public void methodTwo() {
        try {
        } catch (java.lang.RuntimeException e) { // warn
        }
    }
}
