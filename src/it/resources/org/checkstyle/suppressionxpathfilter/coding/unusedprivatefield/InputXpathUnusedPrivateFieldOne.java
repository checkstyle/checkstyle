/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldPattern = (default)serialVersionUID


*/

package org.checkstyle.suppressionxpathfilter.coding.unusedprivatefield;

public class InputXpathUnusedPrivateFieldOne {
    private int unused; // violation 'Unused private field ''unused''\.'
    private int used;

    public int getuse() {
           return used;
       }
}
