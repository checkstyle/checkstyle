package org.checkstyle.suppressionxpathfilter.typecastparenpad;

public class InputXpathTypecastParenPadLeftNotFollowed {
    Object bad = (Object )null;//warn
    Object good = ( Object )null;
}
