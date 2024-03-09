package org.checkstyle.suppressionxpathfilter.unnecessarysemicoloninenumeration;

public class InputXpathUnnecessarySemicolonInEnumeration {
}

enum Good {
    One, Two
}

enum Bad {
    Third; //warn
}
