package org.checkstyle.suppressionxpathfilter.magicnumber;

public class SuppressionXpathRegressionMagicNumberAnotherVariable {
    public void performOperation() {
        try {
            int result = 0;
        } catch(Exception e) {
            int a = 0;
            boolean someCondition = false;
            if (someCondition) {
                a = 1; // ok
            } else {
                a = 20; // warn
            }
        }
    }
}
