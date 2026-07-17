/*
UnusedPrivateField

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

import java.util.function.Supplier;

public class InputUnusedPrivateFieldNestedAccess {

    private static class TimedElement {

        private long startTime;

        private Object element;

        @Override
        public String toString() {
            return element.toString();
        }
    }

    private TimedElement buildElement;

    public void buildStarted() {
        buildElement = new TimedElement();
        buildElement.startTime = System.currentTimeMillis();
        buildElement.element = new Object();
    }

    private int value;

    Supplier<Integer> supplier = () -> value;

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
