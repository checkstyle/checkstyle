package org.checkstyle.suppressionxpathfilter.whitespaceafter;

public class InputXpathWhitespaceAfterTypecast {
    Object bad = (Object)null; //warn
    Object good = (Object) null;
}
