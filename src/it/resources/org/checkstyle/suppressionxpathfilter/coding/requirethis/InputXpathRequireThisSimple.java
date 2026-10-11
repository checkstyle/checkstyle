package org.checkstyle.suppressionxpathfilter.coding.requirethis;

public class InputXpathRequireThisSimple {
    private int age = 23;

    public void changeAge() {
        age = 24; //warn
    }
}
