package org.checkstyle.suppressionxpathfilter.coding.unnecessarysemicoloninenumeration;

public class InputXpathUnnecessarySemicolonInEnumerationSimple {
}

enum Good {
    One, Two
}

enum Bad {
    Third; //warn
}
