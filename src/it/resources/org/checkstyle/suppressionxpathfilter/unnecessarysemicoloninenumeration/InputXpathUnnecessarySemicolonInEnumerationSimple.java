package org.checkstyle.suppressionxpathfilter.unnecessarysemicoloninenumeration;

public class InputXpathUnnecessarySemicolonInEnumerationSimple {
}

enum Good {
    One, Two
}

enum Bad {
    Third; //warn
}
