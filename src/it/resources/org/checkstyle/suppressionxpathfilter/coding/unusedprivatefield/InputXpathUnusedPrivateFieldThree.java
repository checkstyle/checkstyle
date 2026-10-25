/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldPattern = (default)serialVersionUID


*/

package org.checkstyle.suppressionxpathfilter.coding.unusedprivatefield;
public enum InputXpathUnusedPrivateFieldThree {
    ONE, TWO;

    private int unused; // violation 'Unused private field ''unused''\.'
    private int used;

    public int getUsedPrivate() {
        return used;
    }
}
