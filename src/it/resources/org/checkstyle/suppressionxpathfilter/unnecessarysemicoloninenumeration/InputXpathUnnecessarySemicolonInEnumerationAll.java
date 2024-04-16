package org.checkstyle.suppressionxpathfilter.unnecessarysemicoloninenumeration;

enum InputXpathUnnecessarySemicolonInEnumerationAll {
    A,B(){ public String toString() { return "";}},; /** warn */
}
