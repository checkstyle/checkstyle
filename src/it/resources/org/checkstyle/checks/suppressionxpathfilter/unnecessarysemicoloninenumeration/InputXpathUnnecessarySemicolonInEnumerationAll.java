package org.checkstyle.checks.suppressionxpathfilter.unnecessarysemicoloninenumeration;

enum InputXpathUnnecessarySemicolonInEnumerationAll {
    A,B(){ public String toString() { return "";}},; /** warn */
}
