package org.checkstyle.suppressionxpathfilter.whitespace.typecastparenpad;

public class InputXpathTypecastParenPadLeftFollowed {
    Object bad = ( Object)null;//warn
    Object good = (Object)null;
}
