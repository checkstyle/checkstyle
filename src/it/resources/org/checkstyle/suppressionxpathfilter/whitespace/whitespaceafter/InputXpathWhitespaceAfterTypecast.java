package org.checkstyle.suppressionxpathfilter.whitespace.whitespaceafter;

public class InputXpathWhitespaceAfterTypecast {
    Object bad = (Object)null; //warn
    Object good = (Object) null;
}
