package org.checkstyle.suppressionxpathfilter.unnecessarysemicoloninenumeration;

enum All {
    A,B(){ public String toString() { return "";}},; /** warn */
}
