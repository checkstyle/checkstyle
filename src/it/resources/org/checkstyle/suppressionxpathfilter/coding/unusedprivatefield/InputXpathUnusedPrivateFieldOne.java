package org.checkstyle.suppressionxpathfilter.coding.unusedprivatefield;

public class InputXpathUnusedPrivateFieldOne {
    private int unused; // warn
    private int used;

    public int getuse() {
           return used;
       }
}
