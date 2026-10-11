package org.checkstyle.suppressionxpathfilter.whitespace.typecastparenpad;

public class InputXpathTypecastParenPadLeftNotFollowed {
    Object bad = (Object )null;//warn
    Object good = ( Object )null;
}
