package org.checkstyle.suppressionxpathfilter.magicnumber;

public class SuppressionXpathRegressionMagicNumberAnotherVariable {
    public void performOperation() {
        try {
            int result = performCalculation();
        } catch(Exception e) {
            handleException(e);
        }
    }
    private int performCalculation() throws Exception {
        int numerator = 1; // ok
        int denominator = 0; // ok

        if (denominator == 0) {
            throw new Exception("Division by zero");
        }

        return numerator / denominator;
    }

    private void handleException(Exception e) {
        int a = 0;
        boolean someCondition = false;
        if (someCondition) {
            a = 1; // ok
        } else {
            a = 20; // warn
        }
    }
}
