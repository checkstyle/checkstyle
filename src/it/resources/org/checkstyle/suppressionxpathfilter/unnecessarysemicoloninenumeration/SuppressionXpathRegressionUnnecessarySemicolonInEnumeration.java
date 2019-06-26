package org.checkstyle.suppressionxpathfilter.unnecessarysemicoloninenumeration;

public class SuppressionXpathRegressionUnnecessarySemicolonInEnumeration {
}

enum Good {
    One, Two
}

enum Bad {
    Third; //warn
}
