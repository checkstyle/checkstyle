package org.checkstyle.suppressionxpathfilter.coding.illegalcatch;

public class InputXpathIllegalCatchOne {
    public void fun() {
        try {
        } catch (RuntimeException e) { // warn
        }
    }
}
