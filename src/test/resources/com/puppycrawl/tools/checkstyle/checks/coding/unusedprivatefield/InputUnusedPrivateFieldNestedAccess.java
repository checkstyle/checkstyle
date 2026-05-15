/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

import java.util.function.Supplier;

public class InputUnusedPrivateFieldNestedAccess {

    private static class TimedElement {

        private long startTime; // ok, as startTime is used

        private Object element; // ok, as element is used

        @Override
        public String toString() {
            return element.toString();
        }
    }

    private TimedElement buildElement; // ok, as buildElement is used

    public void buildStarted() {
        buildElement = new TimedElement();
        buildElement.startTime = System.currentTimeMillis();
        buildElement.element = new Object();
    }

    private int value;

    Supplier<Integer> supplier = () -> value; // violation, unused private field

    private int counter;

    void test() {
        counter++;
    }

    private int first;
    private int second;
    private int third;

    void test2() {
        first++;
        second++;
        third++;
    }

}
