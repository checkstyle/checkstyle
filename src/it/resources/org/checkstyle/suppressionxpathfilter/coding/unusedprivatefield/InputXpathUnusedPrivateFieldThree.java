package org.checkstyle.suppressionxpathfilter.coding.unusedprivatefield;
public enum InputXpathUnusedPrivateFieldThree {
    ONE, TWO;

    private int unused; // warn
    private int used;

    public int getUsedPrivate() {
        return used;
    }
}
