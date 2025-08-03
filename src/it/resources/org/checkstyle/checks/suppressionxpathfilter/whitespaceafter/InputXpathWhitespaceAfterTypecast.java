package org.checkstyle.checks.suppressionxpathfilter.whitespaceafter;

public class InputXpathWhitespaceAfterTypecast {
    Object bad = (Object)null; //warn
    Object good = (Object) null;
}
