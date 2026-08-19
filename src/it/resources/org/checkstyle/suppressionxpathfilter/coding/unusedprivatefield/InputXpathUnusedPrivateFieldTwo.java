package org.checkstyle.suppressionxpathfilter.coding.unusedprivatefield;

public class InputXpathUnusedPrivateFieldTwo {

    private int used;

    public int getUsed() {
        return used;
    }

    static class Inner {
        private int unused; // warn
        private int usedInInner;

        void bar() {
            System.out.println(usedInInner);
        }
    }
}
