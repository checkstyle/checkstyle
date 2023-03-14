package org.checkstyle.suppressionxpathfilter.typecastparenpad;

public class SuppressionXpathRegressionTypecastParenPadLeftNotFollowed {
    Object bad = (Object )null;//warn
    Object good = ( Object )null;
}
