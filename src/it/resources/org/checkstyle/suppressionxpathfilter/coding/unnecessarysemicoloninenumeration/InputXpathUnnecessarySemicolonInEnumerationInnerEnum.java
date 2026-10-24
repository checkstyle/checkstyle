/*
UnnecessarySemicolonInEnumeration


*/

package org.checkstyle.suppressionxpathfilter.coding.unnecessarysemicoloninenumeration;

public class InputXpathUnnecessarySemicolonInEnumerationInnerEnum {
    enum Status {
        ACTIVE,
        INACTIVE; // violation 'Unnecessary semicolon.'
    }
}
