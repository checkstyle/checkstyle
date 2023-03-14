package org.checkstyle.suppressionxpathfilter.unnecessarysemicoloninenumeration;

enum SuppressionXpathRegressionUnnecessarySemicolonInEnumerationAll {
    A,B(){ public String toString() { return "";}},; /** warn */
}
