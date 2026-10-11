package org.checkstyle.suppressionxpathfilter.coding.unnecessarysemicoloninenumeration;

enum InputXpathUnnecessarySemicolonInEnumerationAll {
    A,B(){ public String toString() { return "";}},; /** warn */
}
