package org.checkstyle.suppressionxpathfilter.coding.requirethis;

public class InputXpathRequireThisOne {
    private int age = 23;

    public void changeAge() {
        age = 24; //warn
    }
}
