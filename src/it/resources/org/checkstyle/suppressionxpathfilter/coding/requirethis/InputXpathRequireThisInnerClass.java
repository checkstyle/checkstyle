package org.checkstyle.suppressionxpathfilter.coding.requirethis;

public class InputXpathRequireThisInnerClass {
    private static class Inner {
        private int age = 23;

        public void changeAge() {
            age = 24; //warn
        }
    }
}
