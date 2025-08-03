package org.checkstyle.checks.suppressionxpathfilter.illegalcatch;

public class InputXpathIllegalCatchOne {
    public void fun() {
        try {
        } catch (RuntimeException e) { // warn
        }
    }
}
