package org.checkstyle.suppressionxpathfilter.coding.unusedprivatefield;

public class InputXpathUnusedPrivateFieldOne {
    private int unused; // warn
    private int used;   // ok

    public int getuse() {
           return used;
       }
}
