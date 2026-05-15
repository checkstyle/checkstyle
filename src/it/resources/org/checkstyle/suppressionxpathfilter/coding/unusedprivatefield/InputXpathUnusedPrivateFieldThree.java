package org.checkstyle.suppressionxpathfilter.coding.unusedprivatefield;

public class InputXpathUnusedPrivateFieldThree {

    private int unusedPrivate; // warn

    private int usedPrivate;

    private static final int CONSTANT = 42;

    protected int protectedField; // ok as it trigger on private

    private int usedInInner;

    public int getUsedInInner() {
        return usedInInner;
    }

    public int getUsedPrivate() {
        return usedPrivate;
    }
}
