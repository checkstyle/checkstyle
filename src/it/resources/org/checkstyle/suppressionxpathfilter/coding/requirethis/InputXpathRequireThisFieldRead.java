package org.checkstyle.suppressionxpathfilter.coding.requirethis;

public class InputXpathRequireThisFieldRead {
    private int age = 23;

    public int getAge() {
        return age;  //warn
    }
}
