/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldPattern = (default)serialVersionUID


*/

package org.checkstyle.suppressionxpathfilter.coding.unusedprivatefield;

public class InputXpathUnusedPrivateFieldTwo {

    private int used;

    public int getUsed() {
        return used;
    }

    static class Inner {
        private int unused; // violation 'Unused private field ''unused''\.'
        private int usedInInner;

        void bar() {
            System.out.println(usedInInner);
        }
    }
}
